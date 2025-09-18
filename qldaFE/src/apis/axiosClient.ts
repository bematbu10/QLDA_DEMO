// axiosClient.ts (đã chỉnh)
import axios, {
  type AxiosResponse,
  type AxiosError,
  type InternalAxiosRequestConfig,
  AxiosInstance,
  AxiosRequestConfig,
} from "axios";
import queryString from "query-string";
import type { ApiResponse } from "../types";

const baseUrlRaw = import.meta.env.VITE_API_URL || "http://localhost:8083/api";
const refreshEndpointRaw = import.meta.env.VITE_REFRESH_TOKEN_ENDPOINT || "/auth/refresh-token";

// build URL safely (avoid double slashes)
const baseUrl = baseUrlRaw.replace(/\/+$/, "");
const refreshPath = refreshEndpointRaw.startsWith("/") ? refreshEndpointRaw.slice(1) : refreshEndpointRaw;
const urlRefreshToken = `${baseUrl}/${refreshPath}`;

const axiosClient = axios.create({
  baseURL: baseUrl,
  headers: {
    "Content-Type": "application/json",
  },
  paramsSerializer: {
    encode: (params) => queryString.stringify(params),
  },
});

// === Helper để refresh token ===
let isRefreshing = false;
let refreshSubscribers: ((token: string) => void)[] = [];

function onTokenRefreshed(token: string) {
  refreshSubscribers.forEach((cb) => cb(token));
  refreshSubscribers = [];
}

function addRefreshSubscriber(cb: (token: string) => void) {
  refreshSubscribers.push(cb);
}

// === Request interceptor ===
axiosClient.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem("access_token");
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error: AxiosError) => Promise.reject(error)
);

// === Response interceptor ===
axiosClient.interceptors.response.use(
  (response: AxiosResponse<ApiResponse<any>>) => {
    const apiResponse = response.data;
    if (apiResponse && apiResponse.status === true) {
      return apiResponse.data;
    }
    return Promise.reject({
      message: apiResponse?.message ?? "Unknown API response",
      statusCode: apiResponse?.statusCode,
    });
  },
  async (error: AxiosError) => {
    // debug helper: uncomment khi cần
     console.log("!!! INTERCEPTOR CAUGHT AN ERROR !!!");
    console.log("Error object:", JSON.parse(JSON.stringify(error)));
    console.log("Error response status:", error.response);

    console.log("[axios] response error", {
      status: error.response?.status,
      data: error.response?.data,
      url: error.config?.url,
    });

    const originalRequest = error.config as InternalAxiosRequestConfig & { _retry?: boolean };

    if (!originalRequest) {
      return Promise.reject(error);
    }

    const status = error.response?.status;
    const bodyMessage = (error.response?.data as any)?.message || (error.response?.data as any)?.error || "";

    // Trigger refresh when 401/403 OR backend sends "expired" message (useful if server returned 500 with expired message)
    const shouldTryRefresh =
      ((status === 401 || status === 403) || /expired/i.test(String(bodyMessage))) &&
      !originalRequest._retry;

    if (shouldTryRefresh) {
      if (isRefreshing) {
        // queue this request until refresh finished
        return new Promise((resolve, reject) => {
          addRefreshSubscriber((token: string) => {
            if (!token) {
              return reject(new Error("No token after refresh"));
            }
            if (originalRequest.headers) {
              originalRequest.headers.Authorization = `Bearer ${token}`;
            }
            resolve(axiosClient(originalRequest));
          });
        });
      }

      originalRequest._retry = true;
      isRefreshing = true;

      try {
        const refreshToken = localStorage.getItem("refresh_token");
        if (!refreshToken) throw new Error("Missing refresh token");

        console.debug("[axios] refreshing token using:", urlRefreshToken);

        // use raw axios (no interceptors) to avoid loops
        const res = await axios.post<ApiResponse<any>>(urlRefreshToken, { refreshToken });

        // Accept both field names to be robust with backend
        const newAccessToken = res.data?.data?.token ?? res.data?.data?.accessToken ?? null;
        const newRefreshToken = res.data?.data?.refreshToken ?? null;

        if (!newAccessToken) {
          // refresh didn't return token -> force logout
          throw new Error("Refresh response did not include access token");
        }

        // Save tokens
        localStorage.setItem("access_token", newAccessToken);
        if (newRefreshToken) localStorage.setItem("refresh_token", newRefreshToken);

        // Notify queued requests
        onTokenRefreshed(newAccessToken);

        // Retry original request with new token
        if (originalRequest.headers) {
          originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
        }
        return axiosClient(originalRequest);
      } catch (err) {
        // refresh failed -> clear storage and redirect to login
        console.warn("[axios] refresh failed, logging out", err);
        localStorage.removeItem("access_token");
        localStorage.removeItem("refresh_token");
        // you may want to use app logout method instead of direct location change
        window.location.href = "/auth";
        return Promise.reject(err);
      } finally {
        isRefreshing = false;
      }
    }

    // Fallback: other errors
    const errorMessage =
      (error.response?.data as { message?: string })?.message ||
      error.message ||
      "An unknown error occurred";

    return Promise.reject({
      message: errorMessage,
      statusCode: error.response?.status,
    });
  }
);

interface CustomAxiosInstance extends AxiosInstance {
  get<T = any, R = T, D = any>(
    url: string,
    config?: AxiosRequestConfig<D>
  ): Promise<R>;
  post<T = any, R = T, D = any>(
    url: string,
    data?: D,
    config?: AxiosRequestConfig<D>
  ): Promise<R>;
  put<T = any, R = T, D = any>(
    url: string,
    data?: D,
    config?: AxiosRequestConfig<D>
  ): Promise<R>;
  delete<T = any, R = T, D = any>(
    url: string,
    config?: AxiosRequestConfig<D>
  ): Promise<R>;
}

export default axiosClient as CustomAxiosInstance;
