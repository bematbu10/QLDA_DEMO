import { useEffect, useState, useCallback } from "react";
import { Project, ProjectTask } from "@/types/project";
import projectService from "@/services/ProjectService";

type ProjectTaskWithProject = ProjectTask & {
  projectId: string;
  projectName?: string;
};

export const useProjects = () => {
  const [projects, setProjects] = useState<Project[]>([]);
  const [isLoading, setLoading] = useState(true);

  const fetchProjects = useCallback(async () => {
    try {
      setLoading(true);
      const data = await projectService.getAll();
      setProjects(data);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchProjects();
  }, [fetchProjects]);

  const getById = (id: string): Project | undefined =>
    projects.find((p) => p.id === id);

  const getAllTasks = (): ProjectTaskWithProject[] => {
    return projects.flatMap((p) =>
      (p.tasks ?? []).map((t) => ({
        ...t,
        projectId: p.id,
        projectName: p.name,
      }))
    );
  };

  return {
    projects,
    isLoading,
    refetch: fetchProjects,
    getById,
    getAllTasks,
  };
};
