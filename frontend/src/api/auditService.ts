import { apiClient, PageResponse } from './client'

export interface ActivityLog {
  id: number
  timestamp: string
  username: string
  action: string
  ipAddress: string
  details: string
  status: string
}

export const auditService = {
  /**
   * Get activity logs (login sessions)
   */
  getActivityLogs: async (
    page: number = 0,
    size: number = 20,
    search?: string,
    action?: string
  ): Promise<PageResponse<ActivityLog>> => {
    const params = new URLSearchParams()
    params.append('page', page.toString())
    params.append('size', size.toString())
    if (search) params.append('search', search)
    if (action && action !== 'all') params.append('action', action)
    
    const response = await apiClient.get(`/audit/activity?${params.toString()}`)
    return response.data
  },

  /**
   * Get data change logs
   */
  getDataChangeLogs: async (
    page: number = 0,
    size: number = 20,
    search?: string
  ): Promise<PageResponse<ActivityLog>> => {
    const params = new URLSearchParams()
    params.append('page', page.toString())
    params.append('size', size.toString())
    if (search) params.append('search', search)
    
    const response = await apiClient.get(`/audit/data-changes?${params.toString()}`)
    return response.data
  }
}
