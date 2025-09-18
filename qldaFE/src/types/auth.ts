import { documentID } from ".";

export type Role = 'admin' | 'manager' | 'staff';
export const ROLES: Role[] = ['admin', 'manager', 'staff'];

export interface LoginPayload {
  username: string;
  password: string;
  isRememberMe: boolean;
}

export interface User {
  id: documentID;
  gender: 'male' | 'female' | 'other';
  birthday: Date;
  username: string;
  email: string;
  phone: string;
  fullName: string;
  role: string | null; // Giả sử role có thể là null
}

export interface LoginData {
  isAuthenticated: boolean;
  token: string;
  refreshToken: string;
  user: User;
}