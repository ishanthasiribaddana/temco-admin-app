import { useState, useEffect, useCallback } from 'react'
import { MessageSquare, Mail, MailOpen, Trash2, CheckCheck, RefreshCw, User, Clock, ChevronDown, ChevronUp, Search } from 'lucide-react'
import { contactMessageService, ContactMessage } from '../../api/contactMessageService'

// ── Mock test data (remove once backend is live) ──────────────────────
const MOCK_MESSAGES: ContactMessage[] = [
  {
    id: 1,
    userProfileId: 101,
    senderName: 'Kamal Perera',
    senderEmail: 'kamal.perera@gmail.com',
    senderPhone: '+94 771 234 567',
    senderNic: '199012345678',
    subject: 'Loans & Lending',
    message: 'I would like to inquire about the student loan interest rates for the 2026 academic year. I am currently enrolled at the University of Colombo and need funding for my final year. Could you please share the eligibility criteria and required documents?',
    isRead: false,
    createdAt: new Date(Date.now() - 15 * 60000).toISOString(), // 15 min ago
  },
  {
    id: 2,
    userProfileId: 102,
    senderName: 'Nimali Fernando',
    senderEmail: 'nimali.f@yahoo.com',
    senderPhone: '+94 712 987 654',
    senderNic: '198534567890',
    subject: 'Deposits & Savings',
    message: 'I have a fixed deposit maturing next month. I want to know the current renewal rates and whether I can switch to a higher-yield savings plan. Please advise.',
    isRead: false,
    createdAt: new Date(Date.now() - 2 * 3600000).toISOString(), // 2 hrs ago
  },
  {
    id: 3,
    userProfileId: 103,
    senderName: 'Ruwan Silva',
    senderEmail: 'ruwan.silva@outlook.com',
    senderPhone: '+94 777 456 789',
    senderNic: '197823456789',
    subject: 'Complaint',
    message: 'I visited the Thalawathugoda branch last Tuesday and experienced very long wait times. The queue management system was not working and there was no staff to assist. I would appreciate it if this could be looked into.',
    isRead: true,
    createdAt: new Date(Date.now() - 2 * 86400000).toISOString(), // 2 days ago
  },
  {
    id: 4,
    userProfileId: 104,
    senderName: 'Ayesha Jayawardena',
    senderEmail: 'ayesha.j@gmail.com',
    senderPhone: '+94 765 321 098',
    senderNic: '200145678901',
    subject: 'Membership',
    message: 'I recently graduated and would like to become a member of TEMCO Bank. What is the process to open a membership account? Do I need to visit a branch or can I apply online?',
    isRead: false,
    createdAt: new Date(Date.now() - 5 * 3600000).toISOString(), // 5 hrs ago
  },
  {
    id: 5,
    userProfileId: 105,
    senderName: 'Dinesh Rathnayake',
    senderEmail: 'dinesh.r@hotmail.com',
    senderPhone: '+94 718 654 321',
    senderNic: '199256789012',
    subject: 'General Inquiry',
    message: 'Can you confirm the operating hours for the World Trade Center head office? I need to visit for a document verification and want to make sure I come during working hours.',
    isRead: true,
    createdAt: new Date(Date.now() - 7 * 86400000).toISOString(), // 7 days ago
  },
]
// ── End mock data ─────────────────────────────────────────────────────

export default function ContactMessages() {
  const [messages, setMessages] = useState<ContactMessage[]>([])
  const [unreadCount, setUnreadCount] = useState(0)
  const [loading, setLoading] = useState(true)
  const [expandedId, setExpandedId] = useState<number | null>(null)
  const [search, setSearch] = useState('')
  const [filter, setFilter] = useState<'all' | 'unread'>('all')

  const fetchMessages = useCallback(async () => {
    setLoading(true)
    try {
      const res = await contactMessageService.getAll(0, 100)
      setMessages(res.data)
      setUnreadCount(res.unreadCount)
    } catch (err) {
      console.warn('API unavailable — loading mock test data:', err)
      setMessages(MOCK_MESSAGES)
      setUnreadCount(MOCK_MESSAGES.filter(m => !m.isRead).length)
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    fetchMessages()
  }, [fetchMessages])

  const handleExpand = async (msg: ContactMessage) => {
    if (expandedId === msg.id) {
      setExpandedId(null)
      return
    }
    setExpandedId(msg.id)
    if (!msg.isRead) {
      try {
        await contactMessageService.markAsRead(msg.id)
        setMessages(prev => prev.map(m => m.id === msg.id ? { ...m, isRead: true } : m))
        setUnreadCount(prev => Math.max(0, prev - 1))
      } catch (err) {
        console.error('Failed to mark as read:', err)
      }
    }
  }

  const handleMarkAllRead = async () => {
    try {
      await contactMessageService.markAllAsRead()
      setMessages(prev => prev.map(m => ({ ...m, isRead: true })))
      setUnreadCount(0)
    } catch (err) {
      console.error('Failed to mark all as read:', err)
    }
  }

  const handleDelete = async (id: number) => {
    if (!confirm('Are you sure you want to delete this message?')) return
    try {
      await contactMessageService.delete(id)
      setMessages(prev => prev.filter(m => m.id !== id))
    } catch (err) {
      console.error('Failed to delete message:', err)
    }
  }

  const filteredMessages = messages
    .filter(m => filter === 'all' || !m.isRead)
    .filter(m => {
      if (!search) return true
      const q = search.toLowerCase()
      return (
        m.senderName?.toLowerCase().includes(q) ||
        m.senderEmail?.toLowerCase().includes(q) ||
        m.subject?.toLowerCase().includes(q) ||
        m.message?.toLowerCase().includes(q)
      )
    })

  const formatDate = (dateStr: string) => {
    const d = new Date(dateStr)
    const now = new Date()
    const diffMs = now.getTime() - d.getTime()
    const diffMins = Math.floor(diffMs / 60000)
    if (diffMins < 1) return 'Just now'
    if (diffMins < 60) return `${diffMins}m ago`
    const diffHrs = Math.floor(diffMins / 60)
    if (diffHrs < 24) return `${diffHrs}h ago`
    const diffDays = Math.floor(diffHrs / 24)
    if (diffDays < 7) return `${diffDays}d ago`
    return d.toLocaleDateString('en-LK', { year: 'numeric', month: 'short', day: 'numeric' })
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-gray-900 flex items-center gap-2">
            <MessageSquare className="w-6 h-6 text-blue-600" />
            Contact Messages
          </h1>
          <p className="text-sm text-gray-500 mt-1">
            {unreadCount > 0 ? `${unreadCount} unread message${unreadCount > 1 ? 's' : ''}` : 'All messages read'}
          </p>
        </div>
        <div className="flex items-center gap-2">
          <button
            onClick={fetchMessages}
            className="inline-flex items-center gap-1.5 px-3 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50"
          >
            <RefreshCw className={`w-4 h-4 ${loading ? 'animate-spin' : ''}`} />
            Refresh
          </button>
          {unreadCount > 0 && (
            <button
              onClick={handleMarkAllRead}
              className="inline-flex items-center gap-1.5 px-3 py-2 text-sm font-medium text-blue-700 bg-blue-50 border border-blue-200 rounded-lg hover:bg-blue-100"
            >
              <CheckCheck className="w-4 h-4" />
              Mark all read
            </button>
          )}
        </div>
      </div>

      {/* Filters */}
      <div className="flex flex-col sm:flex-row gap-3">
        <div className="relative flex-1">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-400" />
          <input
            type="text"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Search messages..."
            className="w-full pl-10 pr-4 py-2.5 text-sm border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 outline-none"
          />
        </div>
        <div className="flex rounded-lg border border-gray-300 overflow-hidden">
          <button
            onClick={() => setFilter('all')}
            className={`px-4 py-2.5 text-sm font-medium ${filter === 'all' ? 'bg-blue-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-50'}`}
          >
            All ({messages.length})
          </button>
          <button
            onClick={() => setFilter('unread')}
            className={`px-4 py-2.5 text-sm font-medium border-l ${filter === 'unread' ? 'bg-blue-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-50'}`}
          >
            Unread ({unreadCount})
          </button>
        </div>
      </div>

      {/* Messages List */}
      {loading ? (
        <div className="flex items-center justify-center py-20">
          <RefreshCw className="w-6 h-6 text-blue-600 animate-spin" />
          <span className="ml-2 text-gray-500">Loading messages...</span>
        </div>
      ) : filteredMessages.length === 0 ? (
        <div className="text-center py-20 bg-white rounded-xl border border-gray-200">
          <Mail className="w-12 h-12 text-gray-300 mx-auto mb-3" />
          <h3 className="text-lg font-semibold text-gray-700">No messages</h3>
          <p className="text-sm text-gray-400 mt-1">
            {filter === 'unread' ? 'All messages have been read.' : 'No contact messages yet.'}
          </p>
        </div>
      ) : (
        <div className="space-y-2">
          {filteredMessages.map((msg) => (
            <div
              key={msg.id}
              className={`bg-white rounded-xl border transition-all duration-200 ${
                !msg.isRead
                  ? 'border-blue-200 shadow-sm ring-1 ring-blue-100'
                  : 'border-gray-200 hover:border-gray-300'
              }`}
            >
              {/* Message Header Row */}
              <button
                onClick={() => handleExpand(msg)}
                className="w-full flex items-center gap-4 p-4 text-left"
              >
                <div className={`w-10 h-10 rounded-full flex items-center justify-center flex-shrink-0 ${
                  !msg.isRead ? 'bg-blue-100' : 'bg-gray-100'
                }`}>
                  {!msg.isRead ? (
                    <Mail className="w-5 h-5 text-blue-600" />
                  ) : (
                    <MailOpen className="w-5 h-5 text-gray-400" />
                  )}
                </div>
                <div className="flex-1 min-w-0">
                  <div className="flex items-center gap-2">
                    <span className={`text-sm truncate ${!msg.isRead ? 'font-bold text-gray-900' : 'font-medium text-gray-700'}`}>
                      {msg.senderName || 'Unknown'}
                    </span>
                    {!msg.isRead && (
                      <span className="inline-block w-2 h-2 rounded-full bg-blue-600 flex-shrink-0" />
                    )}
                  </div>
                  <p className={`text-sm truncate ${!msg.isRead ? 'font-semibold text-gray-800' : 'text-gray-600'}`}>
                    {msg.subject}
                  </p>
                  <p className="text-xs text-gray-400 truncate mt-0.5">{msg.message}</p>
                </div>
                <div className="flex items-center gap-3 flex-shrink-0">
                  <span className="text-xs text-gray-400 whitespace-nowrap">
                    <Clock className="w-3 h-3 inline mr-1" />
                    {formatDate(msg.createdAt)}
                  </span>
                  {expandedId === msg.id ? (
                    <ChevronUp className="w-4 h-4 text-gray-400" />
                  ) : (
                    <ChevronDown className="w-4 h-4 text-gray-400" />
                  )}
                </div>
              </button>

              {/* Expanded Content */}
              {expandedId === msg.id && (
                <div className="px-4 pb-4 border-t border-gray-100">
                  <div className="mt-4 grid sm:grid-cols-3 gap-3 mb-4">
                    <div className="flex items-center gap-2 text-sm">
                      <User className="w-4 h-4 text-gray-400" />
                      <span className="text-gray-500">Name:</span>
                      <span className="font-medium text-gray-800">{msg.senderName}</span>
                    </div>
                    <div className="flex items-center gap-2 text-sm">
                      <Mail className="w-4 h-4 text-gray-400" />
                      <span className="text-gray-500">Email:</span>
                      <a href={`mailto:${msg.senderEmail}`} className="font-medium text-blue-600 hover:underline">
                        {msg.senderEmail || '—'}
                      </a>
                    </div>
                    <div className="flex items-center gap-2 text-sm">
                      <span className="text-gray-500">NIC:</span>
                      <span className="font-medium text-gray-800">{msg.senderNic || '—'}</span>
                    </div>
                  </div>
                  <div className="bg-gray-50 rounded-lg p-4 mb-4">
                    <p className="text-sm text-gray-700 whitespace-pre-wrap leading-relaxed">{msg.message}</p>
                  </div>
                  <div className="flex items-center justify-between">
                    <span className="text-xs text-gray-400">
                      Received: {new Date(msg.createdAt).toLocaleString('en-LK')}
                    </span>
                    <button
                      onClick={() => handleDelete(msg.id)}
                      className="inline-flex items-center gap-1.5 px-3 py-1.5 text-xs font-medium text-red-600 bg-red-50 border border-red-200 rounded-lg hover:bg-red-100"
                    >
                      <Trash2 className="w-3.5 h-3.5" />
                      Delete
                    </button>
                  </div>
                </div>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
