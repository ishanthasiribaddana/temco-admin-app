import { apiClient } from './client'

export interface NicHintResponse {
  hint: string
  found: boolean
}

export interface ChangePasswordRequest {
  username: string
  currentPassword: string
  newPassword: string
}

export const memberAuthService = {
  /**
   * Get partial NIC hint for login page
   */
  getNicHint: async (email: string): Promise<NicHintResponse> => {
    const response = await apiClient.get(`/member-auth/nic-hint?email=${encodeURIComponent(email)}`)
    return response.data
  },

  /**
   * Check if user must change password on first login
   */
  mustChangePassword: async (username: string): Promise<boolean> => {
    const response = await apiClient.get(`/member-auth/must-change-password?username=${encodeURIComponent(username)}`)
    return response.data.mustChangePassword
  },

  /**
   * Change password (first login or regular)
   */
  changePassword: async (request: ChangePasswordRequest): Promise<{ success: boolean; message?: string; error?: string }> => {
    const response = await apiClient.post('/member-auth/change-password', request)
    return response.data
  },

  /**
   * Generate login accounts for all members (admin only)
   */
  generateLogins: async (): Promise<{ success: boolean; created: number; message: string }> => {
    const response = await apiClient.post('/member-auth/generate-logins')
    return response.data
  }
}
