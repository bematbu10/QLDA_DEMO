import { LoginPayload, User } from '@/types/auth';
import React, { createContext, useState, useContext, useEffect, ReactNode } from 'react';
import authService from '@/services/authService';

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  login: (payload: LoginPayload) => Promise<void>; // <-- Kiểu payload đã được cập nhật
  logout: () => void;
  forgot: () => void;
  signUp: () => Promise<void>;
  isLoading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);

  useEffect(() => {
    try {
      // Dùng hàm từ service để lấy người dùng đã lưu
      const currentUser = authService.getCurrentUser();
      if (currentUser) {
        setUser(currentUser);
      }
    } catch (error) {
      console.error("Không thể lấy thông tin người dùng:", error);
      // Nếu có lỗi, đảm bảo trạng thái là đã đăng xuất
      setUser(null); 
    } finally {
      // Dù thành công hay thất bại, cũng phải kết thúc loading
      setIsLoading(false);
    }
  }, []); 

  // Hàm đăng nhập
  const login = async (payload: LoginPayload) => {
    // Gọi service
    const response = await authService.login(payload);
    // Cập nhật state với user từ response
    setUser(response.user);
  };

  const logout = () => {
    authService.logout();
    setUser(null);
  };

  const forgot = () => {
    authService.logout();
    setUser(null);
  };

  const signUp = async () => {
    authService.signUp(); 
  };

  const value = {
    user,
    isAuthenticated: !!user,
    login,
    logout,
    forgot,
    signUp,
    isLoading,
  };

  return (
    <AuthContext.Provider value={value}>
      {!isLoading && children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth phải được sử dụng bên trong một AuthProvider');
  }
  return context;
};