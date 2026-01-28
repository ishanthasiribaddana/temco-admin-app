import { apiClient, PageResponse } from './client'

export interface Member {
  id: number
  membershipNo: string
  firstName: string | null
  lastName: string | null
  fullName: string | null
  email: string | null
  nic: string | null
  isActive: boolean
}

export const memberService = {
  getMembers: async (
    page: number = 0,
    size: number = 20,
    search?: string
  ): Promise<PageResponse<Member>> => {
    const params = new URLSearchParams()
    params.append('page', page.toString())
    params.append('size', size.toString())
    if (search) params.append('search', search)
    
    const response = await apiClient.get(`/members?${params.toString()}`)
    return response.data
  }
}
