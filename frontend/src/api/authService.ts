import { apiClient } from './client'

export interface LoginRequest {
  username: string
  password: string
}

export interface AuthResponse {
  userId: number
  username: string
  email: string
  fullName: string
  roleName: string
  permissions: string[]
  accessToken: string
  refreshToken: string
}

export const authService = {
  async login(data: LoginRequest): Promise<AuthResponse> {
    const response = await apiClient.post<AuthResponse>('/auth/login', data)
    return response.data
  },

  async logout(): Promise<void> {
    await apiClient.post('/auth/logout')
  },

  async getCurrentUser(): Promise<AuthResponse> {
    const response = await apiClient.get<AuthResponse>('/auth/me')
    return response.data
  },

  async changePassword(oldPassword: string, newPassword: string): Promise<void> {
    await apiClient.post('/auth/change-password', { oldPassword, newPassword })
  },
}
