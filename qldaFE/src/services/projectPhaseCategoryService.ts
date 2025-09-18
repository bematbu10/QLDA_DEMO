// src/services/projectService.ts

import apiService from '@/apis/apiService';
import { Project, ProjectPayload, ProjectPhase } from '@/types/project';

const PROJECT_PHASE_ENDPOINT = '/project-phase-category/getAll';
const PROJECT_PHASE_ENDPOINT_UPDATE = '/project-phase-category/update';
const PROJECT_PHASE_ENDPOINT_CREATE = '/project-phase-category/add';

const projectPhaseCategoryService = {
  /**
   * Lấy danh sách tất cả các dự án.
   */
  getAll: (): Promise<ProjectPhase[]> => {
    return apiService.get<ProjectPhase[]>(PROJECT_PHASE_ENDPOINT);
  },

  /**
   * Lấy chi tiết một dự án bằng ID.
   */
  getById: (id: string): Promise<ProjectPhase> => {
    return apiService.get<ProjectPhase>(`${PROJECT_PHASE_ENDPOINT}/${id}`);
  },

  /**
   * Tạo một dự án mới.
   * @param payload Dữ liệu của dự án mới
   */
  create: (payload: ProjectPhase): Promise<ProjectPhase> => {
    return apiService.post<ProjectPhase>(PROJECT_PHASE_ENDPOINT_CREATE, payload);
  },

  /**
   * Cập nhật một dự án đã có.
   * @param id ID của dự án cần cập nhật
   * @param payload Dữ liệu cần cập nhật
   */
  update: (id: string, payload: Partial<ProjectPhase>): Promise<ProjectPhase> => {
    return apiService.put<ProjectPhase>(`${PROJECT_PHASE_ENDPOINT_UPDATE}/${id}`, payload);
  },

  /**
   * Xóa một dự án.
   * @param id ID của dự án cần xóa
   */
  delete: (id: string): Promise<void> => {
    return apiService.delete<void>(`${PROJECT_PHASE_ENDPOINT}/${id}`);
  },
};

export default projectPhaseCategoryService;