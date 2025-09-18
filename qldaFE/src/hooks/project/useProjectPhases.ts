import { useEffect, useState } from "react"
import { Project, ProjectPhase, ProjectTemplate } from "@/types/project"
import projectService from "@/services/ProjectService"
import projectPhaseCategoryService from "@/services/projectPhaseCategoryService"

export const useProjectPhases = () => {
  const [projectPhases, setProjectPhases] = useState<ProjectPhase[]>([])
  const [loading, setLoading] = useState(true)

  const fetchTemplates = async () => {
    setLoading(true)
    try {
      const data = await projectPhaseCategoryService.getAll()
      setProjectPhases(data)
    } catch (error) {
      console.error("Lỗi khi lấy project templates:", error)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchTemplates()
  }, [])

  return { projectPhases, loading, refetch: fetchTemplates }
}
