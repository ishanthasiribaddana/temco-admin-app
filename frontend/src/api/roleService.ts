import { apiClient, PageResponse } from './client'

export interface Role {
  id: number
  roleCode: string
  roleName: string
  description: string
  userCount: number
  permissionCount: number
  isActive: boolean
}

export interface CreateRoleRequest {
  roleCode: string
  roleName: string
  description?: string
}

export interface UpdateRoleRequest {
  roleCode: string
  roleName: string
  description?: string
}

export const roleService = {
  async getAll(page = 0, size = 20, search?: string): Promise<PageResponse<Role>> {
    const params = new URLSearchParams({ page: String(page), size: String(size) })
    if (search) params.append('search', search)
    
    const response = await apiClient.get<PageResponse<Role>>(`/roles?${params}`)
    return response.data
  },

  async getById(id: number): Promise<Role> {
    const response = await apiClient.get<Role>(`/roles/${id}`)
    return response.data
  },

  async create(data: CreateRoleRequest): Promise<Role> {
    const response = await apiClient.post<Role>('/roles', data)
    return response.data
  },

  async update(id: number, data: UpdateRoleRequest): Promise<Role> {
    const response = await apiClient.put<Role>(`/roles/${id}`, data)
    return response.data
  },

  async delete(id: number): Promise<void> {
    await apiClient.delete(`/roles/${id}`)
  },
}
