import { useEffect, useState } from "react"
import { Project, ProjectTemplate } from "@/types/project"
import projectService from "@/services/ProjectService"

export const useProjectTemplates = () => {
  const [projectTemplates, setProjectTemplates] = useState<Project[]>([])
  const [loading, setLoading] = useState(true)

  const fetchTemplates = async () => {
    setLoading(true)
    try {
      //TODO: chưa có getAllProjectsTemplate trong service
      const data = await projectService.getAll()
      setProjectTemplates(data)
    } catch (error) {
      console.error("Lỗi khi lấy project templates:", error)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchTemplates()
  }, [])

  return { projectTemplates, loading, refetch: fetchTemplates }
}
