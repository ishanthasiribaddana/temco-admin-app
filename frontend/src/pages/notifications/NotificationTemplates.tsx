import { useState } from 'react'
import { Plus, Edit2, Trash2, Mail, MessageSquare, Bell, Copy } from 'lucide-react'

interface NotificationTemplate {
  id: number
  name: string
  type: 'email' | 'sms' | 'push'
  subject: string
  content: string
  variables: string[]
  isActive: boolean
}

const mockTemplates: NotificationTemplate[] = [
  { id: 1, name: 'Loan Approval', type: 'email', subject: 'Your Loan Has Been Approved', content: 'Dear {{name}}, Your loan application #{{loan_id}} for Rs. {{amount}} has been approved.', variables: ['name', 'loan_id', 'amount'], isActive: true },
  { id: 2, name: 'Payment Reminder', type: 'sms', subject: '', content: 'Hi {{name}}, Your payment of Rs. {{amount}} is due on {{due_date}}. Please make the payment to avoid penalties.', variables: ['name', 'amount', 'due_date'], isActive: true },
  { id: 3, name: 'Overdue Notice', type: 'email', subject: 'Payment Overdue - Immediate Action Required', content: 'Dear {{name}}, Your payment is {{days}} days overdue. Please settle the amount of Rs. {{amount}} immediately.', variables: ['name', 'days', 'amount'], isActive: true },
  { id: 4, name: 'Welcome Message', type: 'push', subject: '', content: 'Welcome to TEMCO Bank, {{name}}! Your account is now active.', variables: ['name'], isActive: false },
]

const typeConfig = {
  email: { icon: Mail, label: 'Email', color: 'bg-blue-100 text-blue-700' },
  sms: { icon: MessageSquare, label: 'SMS', color: 'bg-green-100 text-green-700' },
  push: { icon: Bell, label: 'Push', color: 'bg-purple-100 text-purple-700' },
}

export default function NotificationTemplates() {
  const [templates] = useState(mockTemplates)
  const [selectedTemplate, setSelectedTemplate] = useState<NotificationTemplate | null>(null)

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Notification Templates</h1>
          <p className="text-secondary-600 mt-1">Manage notification message templates</p>
        </div>
        <button className="btn btn-primary flex items-center gap-2">
          <Plus className="h-4 w-4" />
          New Template
        </button>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-2">
          <div className="card">
            <div className="card-body">
              <div className="space-y-4">
                {templates.map((template) => {
                  const config = typeConfig[template.type]
                  const TypeIcon = config.icon
                  return (
                    <div
                      key={template.id}
                      onClick={() => setSelectedTemplate(template)}
                      className={`p-4 rounded-lg border cursor-pointer transition-colors ${
                        selectedTemplate?.id === template.id
                          ? 'border-primary-500 bg-primary-50'
                          : 'border-secondary-200 hover:bg-secondary-50'
                      }`}
                    >
                      <div className="flex items-start justify-between">
                        <div className="flex items-start gap-3">
                          <div className={`p-2 rounded-lg ${config.color}`}>
                            <TypeIcon className="h-4 w-4" />
                          </div>
                          <div>
                            <div className="flex items-center gap-2">
                              <h3 className="font-medium text-secondary-900">{template.name}</h3>
                              <span className={`px-2 py-0.5 rounded text-xs ${config.color}`}>
                                {config.label}
                              </span>
                              {!template.isActive && (
                                <span className="px-2 py-0.5 rounded text-xs bg-gray-100 text-gray-600">
                                  Inactive
                                </span>
                              )}
                            </div>
                            {template.subject && (
                              <p className="text-sm text-secondary-600 mt-1">{template.subject}</p>
                            )}
                            <p className="text-sm text-secondary-500 mt-2 line-clamp-2">{template.content}</p>
                          </div>
                        </div>
                        <div className="flex items-center gap-1">
                          <button className="p-2 hover:bg-secondary-100 rounded-lg">
                            <Edit2 className="h-4 w-4 text-secondary-500" />
                          </button>
                          <button className="p-2 hover:bg-red-50 rounded-lg">
                            <Trash2 className="h-4 w-4 text-red-500" />
                          </button>
                        </div>
                      </div>
                    </div>
                  )
                })}
              </div>
            </div>
          </div>
        </div>

        <div>
          <div className="card sticky top-6">
            <div className="card-body">
              <h3 className="text-lg font-semibold text-secondary-900 mb-4">Template Preview</h3>
              {selectedTemplate ? (
                <div className="space-y-4">
                  <div>
                    <label className="text-sm font-medium text-secondary-700">Name</label>
                    <p className="text-secondary-900">{selectedTemplate.name}</p>
                  </div>
                  <div>
                    <label className="text-sm font-medium text-secondary-700">Type</label>
                    <p className="text-secondary-900 capitalize">{selectedTemplate.type}</p>
                  </div>
                  {selectedTemplate.subject && (
                    <div>
                      <label className="text-sm font-medium text-secondary-700">Subject</label>
                      <p className="text-secondary-900">{selectedTemplate.subject}</p>
                    </div>
                  )}
                  <div>
                    <label className="text-sm font-medium text-secondary-700">Content</label>
                    <div className="mt-2 p-3 bg-secondary-50 rounded-lg text-sm text-secondary-700">
                      {selectedTemplate.content}
                    </div>
                  </div>
                  <div>
                    <label className="text-sm font-medium text-secondary-700">Variables</label>
                    <div className="flex flex-wrap gap-2 mt-2">
                      {selectedTemplate.variables.map((variable) => (
                        <span
                          key={variable}
                          className="inline-flex items-center gap-1 px-2 py-1 bg-secondary-100 text-secondary-700 rounded text-xs font-mono"
                        >
                          {`{{${variable}}}`}
                          <Copy className="h-3 w-3 cursor-pointer hover:text-primary-600" />
                        </span>
                      ))}
                    </div>
                  </div>
                </div>
              ) : (
                <div className="text-center py-8">
                  <Mail className="h-12 w-12 text-secondary-300 mx-auto mb-4" />
                  <p className="text-secondary-500">Select a template to preview</p>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
