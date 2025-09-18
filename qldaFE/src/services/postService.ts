import apiService from "@/apis/apiService";
import { Post } from "@/types";


// Định nghĩa kiểu cho payload tạo bài viết mới
type CreatePostPayload = Omit<Post, 'id'>;

// Định nghĩa kiểu cho payload cập nhật bài viết
type UpdatePostPayload = Partial<Post>; // Partial làm cho mọi thuộc tính là tùy chọn

const postService = {
  // Lấy tất cả bài viết, có thể có phân trang
  getAll: (params?: { _limit?: number; _page?: number }): Promise<Post[]> => {
    return apiService.get<Post[]>('/posts', params);
  },

  // Lấy một bài viết theo ID
  getById: (id: number): Promise<Post> => {
    return apiService.get<Post>(`/posts/${id}`);
  },

  // Tạo một bài viết mới
  create: (newPost: CreatePostPayload): Promise<Post> => {
    return apiService.post<Post>('/posts', newPost);
  },

  // Cập nhật một bài viết
  update: (id: number, updatedPost: UpdatePostPayload): Promise<Post> => {
    return apiService.put<Post>(`/posts/${id}`, updatedPost);
  },

  // Xóa một bài viết
  delete: (id: number): Promise<{}> => { // API delete thường trả về object rỗng
    return apiService.delete<{}>(`/posts/${id}`);
  },
};

export default postService;