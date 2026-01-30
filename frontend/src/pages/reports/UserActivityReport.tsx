import { useState } from 'react'
import { Search, Download, Users, Clock, Activity, LogIn } from 'lucide-react'

interface UserActivity {
  id: number
  userName: string
  role: string
  lastLogin: string
  totalLogins: number
  activeHours: number
  actionsToday: number
  status: 'online' | 'offline' | 'away'
}

const mockActivities: UserActivity[] = [
  { id: 1, userName: 'Admin User', role: 'Super Admin', lastLogin: '2026-01-30 09:15', totalLogins: 245, activeHours: 6.5, actionsToday: 42, status: 'online' },
  { id: 2, userName: 'Gevindu Silva', role: 'Accountant', lastLogin: '2026-01-30 08:30', totalLogins: 180, activeHours: 5.2, actionsToday: 28, status: 'online' },
  { id: 3, userName: 'Prasanna Kumar', role: 'Accountant', lastLogin: '2026-01-29 17:45', totalLogins: 156, activeHours: 0, actionsToday: 0, status: 'offline' },
  { id: 4, userName: 'Costa Fernando', role: 'Accountant', lastLogin: '2026-01-30 10:00', totalLogins: 198, activeHours: 2.1, actionsToday: 15, status: 'away' },
]

const statusConfig = {
  online: { label: 'Online', color: 'bg-green-500' },
  offline: { label: 'Offline', color: 'bg-gray-400' },
  away: { label: 'Away', color: 'bg-yellow-500' },
}

export default function UserActivityReport() {
  const [searchTerm, setSearchTerm] = useState('')

  const filteredActivities = mockActivities.filter(activity =>
    activity.userName.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const onlineUsers = mockActivities.filter(u => u.status === 'online').length
  const totalActions = mockActivities.reduce((sum, u) => sum + u.actionsToday, 0)

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">User Activity</h1>
          <p className="text-secondary-600 mt-1">Monitor user engagement and activity</p>
        </div>
        <button className="btn btn-primary flex items-center gap-2">
          <Download className="h-4 w-4" />
          Export Report
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        <div className="card">
          <div className="card-body flex items-center gap-4">
            <div className="p-3 bg-green-100 rounded-xl">
              <Users className="h-6 w-6 text-green-600" />
            </div>
            <div>
              <p className="text-sm text-secondary-600">Online Now</p>
              <p className="text-2xl font-bold text-secondary-900">{onlineUsers}</p>
            </div>
          </div>
        </div>
        <div className="card">
          <div className="card-body flex items-center gap-4">
            <div className="p-3 bg-blue-100 rounded-xl">
              <Activity className="h-6 w-6 text-blue-600" />
            </div>
            <div>
              <p className="text-sm text-secondary-600">Actions Today</p>
              <p className="text-2xl font-bold text-secondary-900">{totalActions}</p>
            </div>
          </div>
        </div>
        <div className="card">
          <div className="card-body flex items-center gap-4">
            <div className="p-3 bg-purple-100 rounded-xl">
              <LogIn className="h-6 w-6 text-purple-600" />
            </div>
            <div>
              <p className="text-sm text-secondary-600">Total Logins (Month)</p>
              <p className="text-2xl font-bold text-secondary-900">779</p>
            </div>
          </div>
        </div>
        <div className="card">
          <div className="card-body flex items-center gap-4">
            <div className="p-3 bg-orange-100 rounded-xl">
              <Clock className="h-6 w-6 text-orange-600" />
            </div>
            <div>
              <p className="text-sm text-secondary-600">Avg. Session</p>
              <p className="text-2xl font-bold text-secondary-900">4.2h</p>
            </div>
          </div>
        </div>
      </div>

      <div className="card">
        <div className="card-body">
          <div className="flex flex-col sm:flex-row gap-4 mb-6">
            <div className="relative flex-1">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-secondary-400" />
              <input
                type="text"
                placeholder="Search users..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="input pl-10 w-full"
              />
            </div>
          </div>

          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b border-secondary-200">
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">User</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Role</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Status</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Last Login</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Total Logins</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Active Hours</th>
                  <th className="text-left py-3 px-4 font-semibold text-secondary-700">Actions Today</th>
                </tr>
              </thead>
              <tbody>
                {filteredActivities.map((activity) => {
                  const status = statusConfig[activity.status]
                  return (
                    <tr key={activity.id} className="border-b border-secondary-100 hover:bg-secondary-50">
                      <td className="py-3 px-4">
                        <div className="flex items-center gap-3">
                          <div className="relative">
                            <div className="h-10 w-10 rounded-full bg-primary-100 flex items-center justify-center">
                              <span className="text-primary-600 font-medium">{activity.userName.charAt(0)}</span>
                            </div>
                            <span className={`absolute bottom-0 right-0 h-3 w-3 rounded-full border-2 border-white ${status.color}`} />
                          </div>
                          <span className="font-medium text-secondary-900">{activity.userName}</span>
                        </div>
                      </td>
                      <td className="py-3 px-4 text-secondary-600">{activity.role}</td>
                      <td className="py-3 px-4">
                        <span className="text-sm text-secondary-600">{status.label}</span>
                      </td>
                      <td className="py-3 px-4 text-secondary-600">{activity.lastLogin}</td>
                      <td className="py-3 px-4 text-secondary-900 font-medium">{activity.totalLogins}</td>
                      <td className="py-3 px-4 text-secondary-600">{activity.activeHours}h</td>
                      <td className="py-3 px-4 text-secondary-900 font-medium">{activity.actionsToday}</td>
                    </tr>
                  )
                })}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  )
}
