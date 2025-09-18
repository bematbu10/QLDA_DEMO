export interface ApiResponse<T> {
  status: boolean;
  statusCode: number;
  message: string;
  data: T; // Dữ liệu thực tế có kiểu là T
}

export interface documentID {
  timestamp: number;
  date: Date; 
}

export interface Post {
  userId: number;
  id: number;
  title: string;
  body: string;
}