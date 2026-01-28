import { useState, useEffect } from 'react'
import { Send, Loader2, Users, FileText, AlertTriangle, CheckCircle, Mail, RefreshCw } from 'lucide-react'
import { emailService, EmailTemplate, MemberEmail, EmailResult } from '../../api/emailService'
import { allMembers } from '../../data/members'
import toast from 'react-hot-toast'

// Convert local members to MemberEmail format (only those with email)
const localMembersWithEmail: MemberEmail[] = allMembers
  .filter(m => m.email && m.email.trim() !== '')
  .map(m => ({
    id: m.id,
    membershipNo: m.membershipNo,
    firstName: m.firstName || '',
    lastName: m.lastName || '',
    fullName: m.fullName || `${m.firstName || ''} ${m.lastName || ''}`.trim(),
    email: m.email!
  }))

export default function EmailCompose() {
  const [templates, setTemplates] = useState<EmailTemplate[]>([])
  const [members, setMembers] = useState<MemberEmail[]>([])
  const [selectedTemplate, setSelectedTemplate] = useState<string>('')
  const [selectedMembers, setSelectedMembers] = useState<number[]>([])
  const [sendToAll, setSendToAll] = useState(false)
  const [subject, setSubject] = useState('')
  const [body, setBody] = useState('')
  const [isLoading, setIsLoading] = useState(true)
  const [isSending, setIsSending] = useState(false)
  const [result, setResult] = useState<EmailResult | null>(null)
  const [searchTerm, setSearchTerm] = useState('')

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    setIsLoading(true)
    try {
      const [templatesData, membersData] = await Promise.all([
        emailService.getTemplates().catch(() => null),
        emailService.getMembersWithEmail().catch(() => null)
      ])
      if (Array.isArray(templatesData) && templatesData.length > 0) {
        setTemplates(templatesData)
      } else {
        throw new Error('No templates from API')
      }
      if (membersData?.content && Array.isArray(membersData.content)) {
        setMembers(membersData.content)
      } else {
        throw new Error('No members from API')
      }
    } catch (error) {
      console.error('Failed to load data from API, using local data:', error)
      // Use local members data as fallback
      setTemplates([
        { id: 'welcome', name: 'Welcome Email', subject: 'Welcome to TEMCO Bank', body: '<h2>Welcome {{fullName}}!</h2><p>Thank you for joining TEMCO Bank. Your membership number is <strong>{{membershipNo}}</strong>.</p><p>Best regards,<br>TEMCO Bank Team</p>' },
        { id: 'loan_offer', name: 'Loan Offer', subject: 'Special Loan Offer for You', body: '<h2>Dear {{fullName}},</h2><p>We have a special loan offer for you!</p><p>As a valued member, you are eligible for our low-interest loans.</p><p>Contact us to learn more.</p><p>Best regards,<br>TEMCO Bank Team</p>' },
        { id: 'payment_reminder', name: 'Payment Reminder', subject: 'Payment Reminder', body: '<h2>Dear {{fullName}},</h2><p>This is a friendly reminder about your upcoming payment.</p><p>Please ensure your account is funded to avoid any late fees.</p><p>Thank you,<br>TEMCO Bank Team</p>' },
        { id: 'newsletter', name: 'Monthly Newsletter', subject: 'TEMCO Bank Newsletter', body: '<h2>Hello {{fullName}},</h2><p>Here is what is new at TEMCO Bank this month!</p><p>Stay tuned for exciting updates and offers.</p><p>Best regards,<br>TEMCO Bank Team</p>' },
        { id: 'custom', name: 'Custom Email', subject: '', body: '' }
      ])
      setMembers(localMembersWithEmail)
      toast.success(`Loaded ${localMembersWithEmail.length} members (offline mode)`)
    } finally {
      setIsLoading(false)
    }
  }

  const handleTemplateChange = (templateId: string) => {
    setSelectedTemplate(templateId)
    const template = templates.find(t => t.id === templateId)
    if (template) {
      setSubject(template.subject)
      setBody(template.body)
    }
  }

  const handleMemberToggle = (memberId: number) => {
    setSelectedMembers(prev =>
      prev.includes(memberId)
        ? prev.filter(id => id !== memberId)
        : [...prev, memberId]
    )
  }

  const handleSelectAll = () => {
    if (selectedMembers.length === filteredMembers.length) {
      setSelectedMembers([])
    } else {
      setSelectedMembers(filteredMembers.map(m => m.id))
    }
  }

  const handleSend = async () => {
    if (!subject.trim()) {
      toast.error('Please enter a subject')
      return
    }
    if (!body.trim()) {
      toast.error('Please enter email content')
      return
    }
    if (!sendToAll && selectedMembers.length === 0) {
      toast.error('Please select at least one member or choose "Send to All"')
      return
    }

    setIsSending(true)
    setResult(null)
    try {
      const emailResult = await emailService.sendEmail({
        memberIds: sendToAll ? undefined : selectedMembers,
        subject,
        body,
        sendToAll
      })
      setResult(emailResult)
      if (emailResult.success) {
        toast.success(emailResult.message)
      } else {
        toast.error(emailResult.message)
      }
    } catch (error) {
      console.error('Failed to send emails:', error)
      toast.error('Failed to send emails')
      setResult({ success: false, message: 'Failed to send emails - server error' })
    } finally {
      setIsSending(false)
    }
  }

  const filteredMembers = members.filter(m =>
    m.fullName?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    m.email?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    m.membershipNo?.includes(searchTerm)
  )

  const recipientCount = sendToAll ? members.length : selectedMembers.length

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <Loader2 className="h-8 w-8 animate-spin text-primary-600" />
      </div>
    )
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Compose Email</h1>
          <p className="text-secondary-600">Send emails to members</p>
        </div>
        <button onClick={loadData} className="btn-secondary">
          <RefreshCw className="h-4 w-4 mr-2" />
          Refresh
        </button>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Recipients Panel */}
        <div className="card">
          <div className="card-body">
            <h3 className="font-semibold text-secondary-900 mb-4 flex items-center">
              <Users className="h-5 w-5 mr-2" />
              Recipients ({members.length} with email)
            </h3>

            <div className="space-y-4">
              <label className="flex items-center p-3 bg-primary-50 rounded-lg cursor-pointer">
                <input
                  type="checkbox"
                  checked={sendToAll}
                  onChange={(e) => {
                    setSendToAll(e.target.checked)
                    if (e.target.checked) setSelectedMembers([])
                  }}
                  className="mr-3 h-4 w-4 text-primary-600 rounded"
                />
                <span className="font-medium text-primary-700">Send to All Members ({members.length})</span>
              </label>

              {!sendToAll && (
                <>
                  <input
                    type="text"
                    placeholder="Search members..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="input"
                  />

                  <div className="flex items-center justify-between text-sm">
                    <span className="text-secondary-600">{selectedMembers.length} selected</span>
                    <button onClick={handleSelectAll} className="text-primary-600 hover:underline">
                      {selectedMembers.length === filteredMembers.length ? 'Deselect All' : 'Select All'}
                    </button>
                  </div>

                  <div className="max-h-64 overflow-y-auto space-y-1 border rounded-lg p-2">
                    {filteredMembers.length === 0 ? (
                      <p className="text-secondary-500 text-center py-4">No members with email found</p>
                    ) : (
                      filteredMembers.map(member => (
                        <label
                          key={member.id}
                          className={`flex items-center p-2 rounded cursor-pointer hover:bg-secondary-50 ${
                            selectedMembers.includes(member.id) ? 'bg-primary-50' : ''
                          }`}
                        >
                          <input
                            type="checkbox"
                            checked={selectedMembers.includes(member.id)}
                            onChange={() => handleMemberToggle(member.id)}
                            className="mr-3 h-4 w-4 text-primary-600 rounded"
                          />
                          <div className="flex-1 min-w-0">
                            <p className="text-sm font-medium text-secondary-900 truncate">{member.fullName}</p>
                            <p className="text-xs text-secondary-500 truncate">{member.email}</p>
                          </div>
                        </label>
                      ))
                    )}
                  </div>
                </>
              )}
            </div>
          </div>
        </div>

        {/* Compose Panel */}
        <div className="lg:col-span-2 space-y-4">
          <div className="card">
            <div className="card-body space-y-4">
              <h3 className="font-semibold text-secondary-900 flex items-center">
                <FileText className="h-5 w-5 mr-2" />
                Email Content
              </h3>

              <div>
                <label className="label block mb-1.5">Template</label>
                <select
                  value={selectedTemplate}
                  onChange={(e) => handleTemplateChange(e.target.value)}
                  className="input"
                >
                  <option value="">Select a template...</option>
                  {templates.map(template => (
                    <option key={template.id} value={template.id}>{template.name}</option>
                  ))}
                </select>
              </div>

              <div>
                <label className="label block mb-1.5">Subject *</label>
                <input
                  type="text"
                  value={subject}
                  onChange={(e) => setSubject(e.target.value)}
                  placeholder="Enter email subject"
                  className="input"
                />
              </div>

              <div>
                <label className="label block mb-1.5">
                  Message * 
                  <span className="text-secondary-400 font-normal ml-2">
                    (Use {'{{fullName}}'}, {'{{membershipNo}}'}, {'{{email}}'} for personalization)
                  </span>
                </label>
                <textarea
                  value={body}
                  onChange={(e) => setBody(e.target.value)}
                  placeholder="Enter email content (HTML supported)"
                  rows={10}
                  className="input font-mono text-sm"
                />
              </div>

              {/* Preview */}
              {body && (
                <div>
                  <label className="label block mb-1.5">Preview</label>
                  <div
                    className="border rounded-lg p-4 bg-white prose prose-sm max-w-none"
                    dangerouslySetInnerHTML={{
                      __html: body
                        .replace(/\{\{fullName\}\}/g, 'John Doe')
                        .replace(/\{\{firstName\}\}/g, 'John')
                        .replace(/\{\{lastName\}\}/g, 'Doe')
                        .replace(/\{\{email\}\}/g, 'john@example.com')
                        .replace(/\{\{membershipNo\}\}/g, '1234567890')
                    }}
                  />
                </div>
              )}
            </div>
          </div>

          {/* Send Button & Result */}
          <div className="card">
            <div className="card-body">
              <div className="flex items-center justify-between">
                <div className="flex items-center text-secondary-600">
                  <Mail className="h-5 w-5 mr-2" />
                  <span>
                    {recipientCount === 0 
                      ? 'No recipients selected' 
                      : `Will send to ${recipientCount} member${recipientCount !== 1 ? 's' : ''}`}
                  </span>
                </div>
                <button
                  onClick={handleSend}
                  disabled={isSending || recipientCount === 0}
                  className="btn-primary"
                >
                  {isSending ? (
                    <>
                      <Loader2 className="h-5 w-5 mr-2 animate-spin" />
                      Sending...
                    </>
                  ) : (
                    <>
                      <Send className="h-5 w-5 mr-2" />
                      Send Email
                    </>
                  )}
                </button>
              </div>

              {result && (
                <div className={`mt-4 p-4 rounded-lg ${result.success ? 'bg-green-50' : 'bg-red-50'}`}>
                  <div className="flex items-start">
                    {result.success ? (
                      <CheckCircle className="h-5 w-5 text-green-600 mr-2 flex-shrink-0" />
                    ) : (
                      <AlertTriangle className="h-5 w-5 text-red-600 mr-2 flex-shrink-0" />
                    )}
                    <div>
                      <p className={`font-medium ${result.success ? 'text-green-800' : 'text-red-800'}`}>
                        {result.message}
                      </p>
                      {result.successCount !== undefined && (
                        <p className="text-sm mt-1">
                          Sent: {result.successCount} | Failed: {result.failCount}
                        </p>
                      )}
                      {result.failures && result.failures.length > 0 && (
                        <details className="mt-2">
                          <summary className="text-sm cursor-pointer hover:underline">
                            View failed recipients
                          </summary>
                          <ul className="text-sm mt-1 list-disc list-inside">
                            {result.failures.map((f, i) => (
                              <li key={i}>{f}</li>
                            ))}
                          </ul>
                        </details>
                      )}
                    </div>
                  </div>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
