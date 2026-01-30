import { useState } from 'react'
import { Bell, Check, Trash2, AlertCircle, Info, CheckCircle } from 'lucide-react'

interface Notification {
  id: number
  title: string
  message: string
  type: 'info' | 'success' | 'warning' | 'error'
  read: boolean
  createdAt: string
}

const mockNotifications: Notification[] = [
  { id: 1, title: 'New Loan Application', message: 'John Perera has submitted a new loan application for Rs. 500,000', type: 'info', read: false, createdAt: '2026-01-30 10:30' },
  { id: 2, title: 'Payment Received', message: 'Payment of Rs. 15,000 received from Mary Silva', type: 'success', read: false, createdAt: '2026-01-30 09:15' },
  { id: 3, title: 'Overdue Payment Alert', message: 'Kumar Fernando has missed payment due on 2026-01-25', type: 'warning', read: true, createdAt: '2026-01-29 17:00' },
  { id: 4, title: 'System Maintenance', message: 'Scheduled maintenance on 2026-02-01 from 2:00 AM to 4:00 AM', type: 'info', read: true, createdAt: '2026-01-28 14:30' },
  { id: 5, title: 'Loan Approved', message: 'Loan application #1234 has been approved', type: 'success', read: true, createdAt: '2026-01-28 11:00' },
]

const typeConfig = {
  info: { icon: Info, bgColor: 'bg-blue-100', iconColor: 'text-blue-600' },
  success: { icon: CheckCircle, bgColor: 'bg-green-100', iconColor: 'text-green-600' },
  warning: { icon: AlertCircle, bgColor: 'bg-yellow-100', iconColor: 'text-yellow-600' },
  error: { icon: AlertCircle, bgColor: 'bg-red-100', iconColor: 'text-red-600' },
}

export default function NotificationCenter() {
  const [notifications, setNotifications] = useState(mockNotifications)
  const [filter, setFilter] = useState<'all' | 'unread'>('all')

  const filteredNotifications = notifications.filter(n => 
    filter === 'all' || (filter === 'unread' && !n.read)
  )

  const unreadCount = notifications.filter(n => !n.read).length

  const markAsRead = (id: number) => {
    setNotifications(prev => prev.map(n => n.id === id ? { ...n, read: true } : n))
  }

  const markAllAsRead = () => {
    setNotifications(prev => prev.map(n => ({ ...n, read: true })))
  }

  const deleteNotification = (id: number) => {
    setNotifications(prev => prev.filter(n => n.id !== id))
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Notification Center</h1>
          <p className="text-secondary-600 mt-1">Manage your notifications</p>
        </div>
        <div className="flex items-center gap-4">
          {unreadCount > 0 && (
            <span className="px-3 py-1 bg-primary-100 text-primary-700 rounded-full text-sm font-medium">
              {unreadCount} unread
            </span>
          )}
          <button onClick={markAllAsRead} className="btn btn-secondary flex items-center gap-2">
            <Check className="h-4 w-4" />
            Mark All Read
          </button>
        </div>
      </div>

      <div className="card">
        <div className="card-body">
          <div className="flex gap-4 mb-6 border-b border-secondary-200 pb-4">
            <button
              onClick={() => setFilter('all')}
              className={`px-4 py-2 rounded-lg font-medium transition-colors ${
                filter === 'all' ? 'bg-primary-100 text-primary-700' : 'text-secondary-600 hover:bg-secondary-100'
              }`}
            >
              All Notifications
            </button>
            <button
              onClick={() => setFilter('unread')}
              className={`px-4 py-2 rounded-lg font-medium transition-colors ${
                filter === 'unread' ? 'bg-primary-100 text-primary-700' : 'text-secondary-600 hover:bg-secondary-100'
              }`}
            >
              Unread
            </button>
          </div>

          <div className="space-y-4">
            {filteredNotifications.length === 0 ? (
              <div className="text-center py-12">
                <Bell className="h-12 w-12 text-secondary-300 mx-auto mb-4" />
                <p className="text-secondary-500">No notifications to display</p>
              </div>
            ) : (
              filteredNotifications.map((notification) => {
                const config = typeConfig[notification.type]
                const Icon = config.icon
                return (
                  <div
                    key={notification.id}
                    className={`flex items-start gap-4 p-4 rounded-lg border transition-colors ${
                      notification.read ? 'bg-white border-secondary-200' : 'bg-primary-50 border-primary-200'
                    }`}
                  >
                    <div className={`p-2 rounded-lg ${config.bgColor}`}>
                      <Icon className={`h-5 w-5 ${config.iconColor}`} />
                    </div>
                    <div className="flex-1">
                      <div className="flex items-start justify-between">
                        <div>
                          <h3 className={`font-medium ${notification.read ? 'text-secondary-700' : 'text-secondary-900'}`}>
                            {notification.title}
                          </h3>
                          <p className="text-sm text-secondary-600 mt-1">{notification.message}</p>
                        </div>
                        <span className="text-xs text-secondary-500">{notification.createdAt}</span>
                      </div>
                    </div>
                    <div className="flex items-center gap-2">
                      {!notification.read && (
                        <button
                          onClick={() => markAsRead(notification.id)}
                          className="p-2 hover:bg-secondary-100 rounded-lg"
                          title="Mark as read"
                        >
                          <Check className="h-4 w-4 text-secondary-500" />
                        </button>
                      )}
                      <button
                        onClick={() => deleteNotification(notification.id)}
                        className="p-2 hover:bg-red-50 rounded-lg"
                        title="Delete"
                      >
                        <Trash2 className="h-4 w-4 text-red-500" />
                      </button>
                    </div>
                  </div>
                )
              })
            )}
          </div>
        </div>
      </div>
    </div>
  )
}
