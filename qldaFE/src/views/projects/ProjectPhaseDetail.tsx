import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { FileText } from "lucide-react";
import { ProjectPhase } from "@/types/project";
import { TASK_STATUS_LABELS, TASK_PRIORITY_LABELS } from "@/utils/taskLabels";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
interface ProjectPhaseDetailProps {
  open: boolean;
  onClose: () => void;
  phase: ProjectPhase | null;
}

export function ProjectPhaseDetail({
  open,
  onClose,
  phase,
}: ProjectPhaseDetailProps) {
  if (!phase) return null;

  return (
    <Dialog
      open={open}
      onOpenChange={(isOpen) => {
        if (!isOpen) onClose(); // chỉ đóng khi thật sự set open=false
      }}
    >
      <DialogContent className="w-[90vw] max-w-[1200px] h-[70vh] overflow-y-auto">
        <DialogHeader className="flex justify-between items-center">
          <DialogTitle>Chi tiết giai đoạn</DialogTitle>
        </DialogHeader>

        <Card>
          <CardContent className="p-4">
            <div className="flex items-start justify-between">
              <div className="flex-1">
                <div className="flex items-center gap-2 mb-2">
                  <h4 className="font-semibold">{phase.name}</h4>
                </div>
                <p className="text-sm text-muted-foreground mb-2">
                  {phase.description}
                </p>
                {phase.legalBasis && (
                  <p className="text-xs text-blue-600">
                    Cơ sở pháp lý: {phase.legalBasis}
                  </p>
                )}

                {/* Tài liệu */}
                {phase.documentProjectPhase?.length > 0 && (
                  <div className="mt-4">
                    <h5 className="font-medium mb-2">
                      Tài liệu ({phase.documentProjectPhase.length})
                    </h5>
                    <Table>
                      <TableHeader>
                        <TableRow>
                          <TableHead className="w-[60%]">
                            Tên tài liệu
                          </TableHead>
                          <TableHead>Người tải lên</TableHead>
                          <TableHead>Ngày tải</TableHead>
                          <TableHead className="text-right">Thao tác</TableHead>
                        </TableRow>
                      </TableHeader>
                      <TableBody>
                        {phase.documentProjectPhase.map((doc) => (
                          <TableRow key={doc.id}>
                            <TableCell>
                              <a
                                href={doc.url}
                                target="_blank"
                                rel="noopener noreferrer"
                                className="flex items-center gap-1 text-blue-600 hover:underline "
                              >
                                <FileText className="w-4 h-4" />
                                {doc.name}
                              </a>
                            </TableCell>
                            <TableCell className="text-sm">
                              {doc.uploadedBy}
                            </TableCell>
                            <TableCell className="text-sm">
                              {new Date(doc.uploadedAt).toLocaleDateString(
                                "vi-VN"
                              )}
                            </TableCell>
                            <TableCell className="text-right">
                              <div className="flex gap-2 justify-end items-center">
                                <Button size="sm" variant="outline">
                                  Xem
                                </Button>
                                <Button size="sm" variant="outline">
                                  Xóa
                                </Button>
                              </div>
                            </TableCell>
                          </TableRow>
                        ))}
                      </TableBody>
                    </Table>
                  </div>
                )}

                {/* Công việc */}
                <div className="mt-4">
                  <h5 className="font-medium mb-2">
                    Công việc ({phase.tasks?.length || 0})
                  </h5>
                  <div className="space-y-2">
                    {phase.tasks?.map((task) => (
                      <div
                        key={task.id}
                        className="p-3 border rounded-md bg-muted/40"
                      >
                        <h6 className="font-semibold">{task.name}</h6>
                        <p className="text-sm text-muted-foreground">
                          {task.description}
                        </p>
                        <p className="text-xs">
                          Trạng thái: {TASK_STATUS_LABELS[task.status]} | Ưu
                          tiên: {TASK_PRIORITY_LABELS[task.priority]}
                        </p>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          </CardContent>
        </Card>
      </DialogContent>
    </Dialog>
  );
}
