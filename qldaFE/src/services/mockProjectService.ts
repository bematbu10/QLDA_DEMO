import { ProjectTemplate } from "@/types/project";

let mockData: ProjectTemplate[] = [
  {
    id: "template-1",
    name: "Mẫu dự án Xây dựng",
    phases: [
      {
        id: "phase-1",
        name: "Khảo sát hiện trạng",
        description: "Khảo sát mặt bằng",
        order: 1,
        status: "NOT_STARTED",
        tasks: [
          {
            id: "task-1",
            name: "Đo đạc",
            description: "Đo đạc khu vực dự án",
            status: "NOT_STARTED",
            priority: "medium",
            progress: 0,
          },
        ],
        documentProjectPhase: [],
      },
    ],
    description: "",
    category: "",
  },
  {
    id: "template-2",
    name: "Mẫu dự án CNTT",
    phases: [],
    description: "",
    category: "",
  },
];

export default {
  async getAll(): Promise<ProjectTemplate[]> {
    return Promise.resolve(mockData);
  },

  async update(id: string, updated: ProjectTemplate): Promise<ProjectTemplate> {
    mockData = mockData.map((t) => (t.id === id ? updated : t));
    return Promise.resolve(updated);
  },
};
