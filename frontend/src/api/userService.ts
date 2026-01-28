import { apiClient, PageResponse } from './client'

export interface User {
  id: number
  username: string
  firstName: string | null
  lastName: string | null
  fullName: string | null
  email: string | null
  roleName: string
  isActive: boolean
  lastLoginAt: string | null
  createdAt: string | null
}

export const userService = {
  getUsers: async (
    page: number = 0,
    size: number = 20,
    search?: string,
    status?: string
  ): Promise<PageResponse<User>> => {
    const params = new URLSearchParams()
    params.append('page', page.toString())
    params.append('size', size.toString())
    if (search) params.append('search', search)
    if (status && status !== 'all') params.append('status', status)
    
    const response = await apiClient.get(`/users?${params.toString()}`)
    return response.data
  }
}
