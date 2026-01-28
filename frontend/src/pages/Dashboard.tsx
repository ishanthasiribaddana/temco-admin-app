import {
  Users,
  Shield,
  FileText,
  Activity,
  TrendingUp,
  TrendingDown,
  Clock,
  AlertTriangle,
  CheckCircle,
  UserCheck,
  Database,
  Server,
} from 'lucide-react'
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, BarChart, Bar } from 'recharts'

const stats = [
  { name: 'Total Members', value: '177', change: '+3%', trend: 'up', icon: Users, color: 'bg-blue-500' },
  { name: 'Active Sessions', value: '12', change: '+5%', trend: 'up', icon: UserCheck, color: 'bg-green-500' },
  { name: 'Roles Defined', value: '8', change: '0%', trend: 'neutral', icon: Shield, color: 'bg-purple-500' },
  { name: 'Audit Events Today', value: '156', change: '+23%', trend: 'up', icon: FileText, color: 'bg-amber-500' },
]

const activityData = [
  { name: '00:00', logins: 120, actions: 340 },
  { name: '04:00', logins: 80, actions: 220 },
  { name: '08:00', logins: 450, actions: 890 },
  { name: '12:00', logins: 380, actions: 720 },
  { name: '16:00', logins: 520, actions: 980 },
  { name: '20:00', logins: 290, actions: 540 },
]

const usersByRole = [
  { role: 'Member', count: 177 },
  { role: 'Accountant', count: 5 },
  { role: 'Admin', count: 1 },
  { role: 'Others', count: 0 },
]

const recentActivities = [
  { id: 1, user: 'John Doe', action: 'Created new user', time: '2 minutes ago', status: 'success' },
  { id: 2, user: 'Jane Smith', action: 'Modified role permissions', time: '5 minutes ago', status: 'success' },
  { id: 3, user: 'Admin User', action: 'Failed login attempt', time: '12 minutes ago', status: 'warning' },
  { id: 4, user: 'System', action: 'Database backup completed', time: '1 hour ago', status: 'success' },
  { id: 5, user: 'Mike Johnson', action: 'Updated system settings', time: '2 hours ago', status: 'success' },
]

const systemHealth = [
  { name: 'Database', status: 'healthy', uptime: '99.99%' },
  { name: 'API Server', status: 'healthy', uptime: '99.95%' },
  { name: 'Cache Server', status: 'healthy', uptime: '100%' },
  { name: 'File Storage', status: 'warning', uptime: '98.5%' },
]

export default function Dashboard() {
  return (
    <div className="space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-2xl font-bold text-secondary-900">Dashboard</h1>
        <p className="text-secondary-600">Welcome to TEMCO Admin Portal</p>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {stats.map((stat) => {
          const Icon = stat.icon
          return (
            <div key={stat.name} className="card card-body">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm font-medium text-secondary-600">{stat.name}</p>
                  <p className="text-2xl font-bold text-secondary-900 mt-1">{stat.value}</p>
                </div>
                <div className={`${stat.color} p-3 rounded-xl`}>
                  <Icon className="h-6 w-6 text-white" />
                </div>
              </div>
              <div className="flex items-center mt-4 text-sm">
                {stat.trend === 'up' ? (
                  <TrendingUp className="h-4 w-4 text-green-500 mr-1" />
                ) : stat.trend === 'down' ? (
                  <TrendingDown className="h-4 w-4 text-red-500 mr-1" />
                ) : (
                  <Activity className="h-4 w-4 text-secondary-400 mr-1" />
                )}
                <span className={stat.trend === 'up' ? 'text-green-600' : stat.trend === 'down' ? 'text-red-600' : 'text-secondary-500'}>
                  {stat.change}
                </span>
                <span className="text-secondary-500 ml-2">from last month</span>
              </div>
            </div>
          )
        })}
      </div>

      {/* Charts Row */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Activity Chart */}
        <div className="lg:col-span-2 card">
          <div className="card-header">
            <h3 className="font-semibold text-secondary-900">System Activity</h3>
          </div>
          <div className="card-body">
            <div className="h-80">
              <ResponsiveContainer width="100%" height="100%">
                <AreaChart data={activityData}>
                  <CartesianGrid strokeDasharray="3 3" stroke="#e2e8f0" />
                  <XAxis dataKey="name" stroke="#64748b" fontSize={12} />
                  <YAxis stroke="#64748b" fontSize={12} />
                  <Tooltip
                    contentStyle={{
                      backgroundColor: '#fff',
                      border: '1px solid #e2e8f0',
                      borderRadius: '8px',
                    }}
                  />
                  <Area
                    type="monotone"
                    dataKey="logins"
                    stackId="1"
                    stroke="#3b82f6"
                    fill="#3b82f6"
                    fillOpacity={0.6}
                    name="Logins"
                  />
                  <Area
                    type="monotone"
                    dataKey="actions"
                    stackId="2"
                    stroke="#10b981"
                    fill="#10b981"
                    fillOpacity={0.6}
                    name="Actions"
                  />
                </AreaChart>
              </ResponsiveContainer>
            </div>
          </div>
        </div>

        {/* Users by Role */}
        <div className="card">
          <div className="card-header">
            <h3 className="font-semibold text-secondary-900">Users by Role</h3>
          </div>
          <div className="card-body">
            <div className="h-80">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={usersByRole} layout="vertical">
                  <CartesianGrid strokeDasharray="3 3" stroke="#e2e8f0" />
                  <XAxis type="number" stroke="#64748b" fontSize={12} />
                  <YAxis dataKey="role" type="category" stroke="#64748b" fontSize={12} width={80} />
                  <Tooltip
                    contentStyle={{
                      backgroundColor: '#fff',
                      border: '1px solid #e2e8f0',
                      borderRadius: '8px',
                    }}
                  />
                  <Bar dataKey="count" fill="#8b5cf6" radius={[0, 4, 4, 0]} name="Users" />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </div>
        </div>
      </div>

      {/* Bottom Row */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Recent Activity */}
        <div className="card">
          <div className="card-header flex items-center justify-between">
            <h3 className="font-semibold text-secondary-900">Recent Activity</h3>
            <a href="/audit/activity" className="text-sm text-primary-600 hover:text-primary-700 font-medium">
              View all
            </a>
          </div>
          <div className="divide-y divide-secondary-100">
            {recentActivities.map((activity) => (
              <div key={activity.id} className="px-6 py-4 flex items-center justify-between">
                <div className="flex items-center space-x-3">
                  <div className={`w-2 h-2 rounded-full ${activity.status === 'success' ? 'bg-green-500' : 'bg-amber-500'}`} />
                  <div>
                    <p className="text-sm font-medium text-secondary-900">{activity.action}</p>
                    <p className="text-xs text-secondary-500">by {activity.user}</p>
                  </div>
                </div>
                <div className="flex items-center text-xs text-secondary-500">
                  <Clock className="h-3 w-3 mr-1" />
                  {activity.time}
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* System Health */}
        <div className="card">
          <div className="card-header">
            <h3 className="font-semibold text-secondary-900">System Health</h3>
          </div>
          <div className="card-body space-y-4">
            {systemHealth.map((system) => (
              <div key={system.name} className="flex items-center justify-between p-4 bg-secondary-50 rounded-lg">
                <div className="flex items-center space-x-3">
                  {system.status === 'healthy' ? (
                    <CheckCircle className="h-5 w-5 text-green-500" />
                  ) : (
                    <AlertTriangle className="h-5 w-5 text-amber-500" />
                  )}
                  <div className="flex items-center space-x-2">
                    {system.name === 'Database' && <Database className="h-4 w-4 text-secondary-400" />}
                    {system.name === 'API Server' && <Server className="h-4 w-4 text-secondary-400" />}
                    {system.name === 'Cache Server' && <Server className="h-4 w-4 text-secondary-400" />}
                    {system.name === 'File Storage' && <Database className="h-4 w-4 text-secondary-400" />}
                    <span className="font-medium text-secondary-900">{system.name}</span>
                  </div>
                </div>
                <div className="text-right">
                  <span className={`badge ${system.status === 'healthy' ? 'badge-success' : 'badge-warning'}`}>
                    {system.status}
                  </span>
                  <p className="text-xs text-secondary-500 mt-1">Uptime: {system.uptime}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}
