import { apiClient } from './client'

export interface EmailConfig {
  smtpHost: string
  smtpPort: number
  username: string
  password: string
  senderEmail: string
  senderName: string
  replyTo: string
  useTls: boolean
  useAuth: boolean
}

export interface EmailTemplate {
  id: string
  name: string
  subject: string
  body: string
}

export interface EmailRequest {
  memberIds?: number[]
  subject: string
  body: string
  templateId?: string
  sendToAll: boolean
}

export interface EmailResult {
  success: boolean
  message: string
  successCount?: number
  failCount?: number
  failures?: string[]
}

export interface MemberEmail {
  id: number
  membershipNo: string
  firstName: string
  lastName: string
  fullName: string
  email: string
}

export const emailService = {
  getConfig: async (): Promise<EmailConfig> => {
    const response = await apiClient.get('/email/config')
    return response.data
  },

  testConnection: async (config: EmailConfig): Promise<{ success: boolean; message: string }> => {
    const response = await apiClient.post('/email/test', config)
    return response.data
  },

  sendEmail: async (request: EmailRequest): Promise<EmailResult> => {
    const response = await apiClient.post('/email/send', request)
    return response.data
  },

  getTemplates: async (): Promise<EmailTemplate[]> => {
    const response = await apiClient.get('/email/templates')
    return response.data
  },

  getMembersWithEmail: async (): Promise<{ content: MemberEmail[]; totalElements: number }> => {
    const response = await apiClient.get('/email/members')
    return response.data
  }
}
