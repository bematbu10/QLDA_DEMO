// src/services/projectService.ts

import apiService from '@/apis/apiService';
import { Project, ProjectPayload } from '@/types/project';

const PROJECT_ENDPOINT = '/projects/getAll';
const PROJECT_ENDPOINT_UPDATE = '/projects/update';
const PROJECT_ENDPOINT_CREATE = '/projects/add';

const projectService = {
  /**
   * Lấy danh sách tất cả các dự án.
   */
  getAll: (): Promise<Project[]> => {
    return apiService.get<Project[]>(PROJECT_ENDPOINT);
  },

  /**
   * Lấy chi tiết một dự án bằng ID.
   */
  getById: (id: string): Promise<Project> => {
    return apiService.get<Project>(`${PROJECT_ENDPOINT}/${id}`);
  },

  /**
   * Tạo một dự án mới.
   * @param payload Dữ liệu của dự án mới
   */
  create: (payload: ProjectPayload): Promise<Project> => {
    return apiService.post<Project>(PROJECT_ENDPOINT_CREATE, payload);
  },

  /**
   * Cập nhật một dự án đã có.
   * @param id ID của dự án cần cập nhật
   * @param payload Dữ liệu cần cập nhật
   */
  update: (id: string, payload: Partial<ProjectPayload>): Promise<Project> => {
    return apiService.put<Project>(`${PROJECT_ENDPOINT_UPDATE}/${id}`, payload);
  },

  /**
   * Xóa một dự án.
   * @param id ID của dự án cần xóa
   */
  delete: (id: string): Promise<void> => {
    return apiService.delete<void>(`${PROJECT_ENDPOINT}/${id}`);
  },
};

export default projectService;