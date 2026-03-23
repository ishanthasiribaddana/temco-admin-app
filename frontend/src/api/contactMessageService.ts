import { apiClient } from './client'

export interface ContactMessage {
  id: number
  userProfileId: number
  senderName: string
  senderEmail: string
  senderPhone: string
  senderNic: string
  subject: string
  message: string
  isRead: boolean
  createdAt: string
}

interface ContactMessagesResponse {
  success: boolean
  data: ContactMessage[]
  unreadCount: number
}

interface UnreadCountResponse {
  unreadCount: number
}

export const contactMessageService = {
  async getAll(page = 0, size = 20): Promise<ContactMessagesResponse> {
    const { data } = await apiClient.get('/contact-messages', { params: { page, size } })
    return data
  },

  async getById(id: number): Promise<ContactMessage> {
    const { data } = await apiClient.get(`/contact-messages/${id}`)
    return data.data
  },

  async getUnreadCount(): Promise<number> {
    const { data } = await apiClient.get<UnreadCountResponse>('/contact-messages/unread-count')
    return data.unreadCount
  },

  async markAsRead(id: number): Promise<void> {
    await apiClient.put(`/contact-messages/${id}/read`)
  },

  async markAllAsRead(): Promise<void> {
    await apiClient.put('/contact-messages/read-all')
  },

  async delete(id: number): Promise<void> {
    await apiClient.delete(`/contact-messages/${id}`)
  },
}
