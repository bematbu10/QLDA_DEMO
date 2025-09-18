import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { Routes, Route, Navigate } from "react-router-dom";

import { AppLayout } from "@/components/layout/AppLayout";
import Dashboard from "../views/pages/DashboardPage";
import Projects from "../views/pages/ProjectsPage";
import Tasks from "../views/pages/TasksPage";
import NotFound from "../views/pages/NotFound";
import AuthScreen from "@/views/login-and-registor/AuthScreen";
import Disbursement from "@/views/pages/DisbursementPage";
import ProjectPhaseTemplatesPage from "@/views/pages/ProjectPhaseTemplatesPage";
import PublicRoute from "@/components/PublicRoute";
import ProtectedRoute from "@/components/ProtectedRoute";
import { Toaster } from "sonner";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster
        richColors   
        position="top-center" // Vị trí hiển thị
        toastOptions={{
          duration: 2000, // Thời gian mặc định
        }}
      />

      <Routes>
        {/* trang public */}
        <Route path="/" element={<Navigate to="/dashboard" replace />} />

        <Route
          path="/auth"
          element={
            // Nếu người dùng đã đăng nhập, họ sẽ bị đẩy ra khỏi trang này
            <PublicRoute>
              <AuthScreen />
            </PublicRoute>
          }
        />

        {/* các trang sau đăng nhập dùng layout chung */}
        <Route
          element={
            <ProtectedRoute>
              <AppLayout />
            </ProtectedRoute>
          }
        >
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/projects" element={<Projects />} />
          <Route path="/tasks" element={<Tasks />} />
          <Route path="/timeline" element={<div>(Sắp Ra Mắt)</div>} />
          <Route path="/disbursement" element={<Disbursement />} />

          <Route path="/phases" element={<ProjectPhaseTemplatesPage />} />

          <Route
            path="/reports"
            element={<div>Trang Báo Cáo (Sắp Ra Mắt)</div>}
          />
          <Route
            path="/tools"
            element={<div>Trang Công Cụ (Sắp Ra Mắt)</div>}
          />
          <Route
            path="/settings"
            element={<div>Trang Cài Đặt (Sắp Ra Mắt)</div>}
          />
        </Route>

        {/* catch-all */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
