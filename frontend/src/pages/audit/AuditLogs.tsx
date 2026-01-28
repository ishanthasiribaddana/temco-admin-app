import { useState, useEffect } from 'react'
import { Search, Filter, Download, Clock, User, Loader2, ChevronLeft, ChevronRight } from 'lucide-react'
import { auditService, ActivityLog } from '../../api/auditService'
import toast from 'react-hot-toast'

const mockLogs = [
  { id: 1, username: 'admin', action: 'LOGIN', details: 'User logged in successfully', ipAddress: '192.168.1.100', timestamp: '2024-01-28 10:30:45', status: 'success' },
  { id: 2, username: 'john.doe', action: 'CREATE', details: 'Created new user: jane.smith', ipAddress: '192.168.1.101', timestamp: '2024-01-28 10:25:30', status: 'success' },
]

const actionColors: Record<string, string> = {
  LOGIN: 'badge-info', CREATE: 'badge-success', UPDATE: 'badge-warning', VIEW: 'bg-secondary-100 text-secondary-800', DELETE: 'badge-danger', IMPERSONATE: 'bg-purple-100 text-purple-800'
}

export default function AuditLogs() {
  const [searchTerm, setSearchTerm] = useState('')
  const [selectedAction, setSelectedAction] = useState('all')
  const [logs, setLogs] = useState<ActivityLog[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [useMockData, setUseMockData] = useState(false)
  const [page, setPage] = useState(0)
  const [totalPages, setTotalPages] = useState(1)
  const [totalElements, setTotalElements] = useState(0)

  const loadLogs = async () => {
    setIsLoading(true)
    try {
      const response = await auditService.getActivityLogs(page, 20, searchTerm, selectedAction)
      setLogs(response.content)
      setTotalPages(response.totalPages)
      setTotalElements(response.totalElements)
      setUseMockData(false)
    } catch (error) {
      console.error('Failed to load logs:', error)
      setLogs(mockLogs as ActivityLog[])
      setUseMockData(true)
      toast.error('Backend unavailable - showing mock data')
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    loadLogs()
  }, [page, selectedAction])

  useEffect(() => {
    const debounce = setTimeout(() => {
      setPage(0)
      loadLogs()
    }, 500)
    return () => clearTimeout(debounce)
  }, [searchTerm])

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Activity Logs</h1>
          <p className="text-secondary-600">Monitor system activity and user actions</p>
        </div>
        <button className="btn-secondary"><Download className="h-5 w-5 mr-2" />Export Logs</button>
      </div>

      <div className="card card-body">
        <div className="flex flex-col sm:flex-row gap-4">
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-secondary-400" />
            <input type="text" placeholder="Search logs..." value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} className="input pl-10" />
          </div>
          <div className="flex items-center gap-2">
            <Filter className="h-5 w-5 text-secondary-400" />
            <select value={selectedAction} onChange={(e) => setSelectedAction(e.target.value)} className="input w-40">
              <option value="all">All Actions</option>
              <option value="LOGIN">Login</option>
              <option value="CREATE">Create</option>
              <option value="UPDATE">Update</option>
              <option value="VIEW">View</option>
              <option value="DELETE">Delete</option>
              <option value="IMPERSONATE">Impersonate</option>
            </select>
          </div>
        </div>
      </div>

      {/* Mock Data Banner */}
      {useMockData && !isLoading && (
        <div className="bg-yellow-50 border border-yellow-200 rounded-lg p-3 text-yellow-800 text-sm">
          ⚠️ Backend unavailable - showing mock data
        </div>
      )}

      {/* Loading State */}
      {isLoading ? (
        <div className="flex justify-center py-12">
          <Loader2 className="h-8 w-8 animate-spin text-blue-600" />
        </div>
      ) : (
        <div className="card overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-secondary-50 border-b border-secondary-200">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Timestamp</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">User</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Action</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Details</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">IP Address</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Status</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-secondary-100">
                {logs.map(log => (
                  <tr key={log.id} className="hover:bg-secondary-50">
                    <td className="px-6 py-4 text-sm text-secondary-600">
                      <div className="flex items-center"><Clock className="h-4 w-4 mr-2 text-secondary-400" />{log.timestamp}</div>
                    </td>
                    <td className="px-6 py-4">
                      <div className="flex items-center"><User className="h-4 w-4 mr-2 text-secondary-400" /><span className="font-medium text-secondary-900">{log.username}</span></div>
                    </td>
                    <td className="px-6 py-4"><span className={`badge ${actionColors[log.action] || 'badge-info'}`}>{log.action}</span></td>
                    <td className="px-6 py-4 text-sm text-secondary-600 max-w-xs truncate">{log.details}</td>
                    <td className="px-6 py-4 text-sm text-secondary-500 font-mono">{log.ipAddress}</td>
                    <td className="px-6 py-4"><span className={`badge ${log.status === 'success' ? 'badge-success' : 'badge-warning'}`}>{log.status}</span></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Pagination */}
          <div className="px-6 py-4 border-t border-secondary-200 flex items-center justify-between">
            <p className="text-sm text-secondary-600">
              Showing {logs.length} of {totalElements} logs
            </p>
            <div className="flex items-center gap-2">
              <button
                onClick={() => setPage(p => Math.max(0, p - 1))}
                disabled={page === 0}
                className="p-2 rounded-lg hover:bg-secondary-100 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <ChevronLeft className="h-5 w-5" />
              </button>
              <span className="text-sm text-secondary-600">
                Page {page + 1} of {totalPages}
              </span>
              <button
                onClick={() => setPage(p => Math.min(totalPages - 1, p + 1))}
                disabled={page >= totalPages - 1}
                className="p-2 rounded-lg hover:bg-secondary-100 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <ChevronRight className="h-5 w-5" />
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
