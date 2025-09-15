import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";
import path from "path";
import { componentTagger } from "lovable-tagger";

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => ({
  server: {
    host: "::",
    port: 8080,
    // Proxy OnlyOffice Document Server to avoid CORS when developing
    proxy: {
      "/oo-cache": {
        target: "http://192.168.100.98:8080",
        changeOrigin: true,
        // remove the prefix so /oo-cache/<path> -> http://192.168.100.98:8080/<path>
        rewrite: (p) => p.replace(/^\/oo-cache/, ""),
      },
    },
  },
  plugins: [
    react(),
    mode === 'development' && componentTagger(),
  ].filter(Boolean),
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
}));
