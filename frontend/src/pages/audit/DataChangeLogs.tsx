import { useState, useEffect } from 'react'
import { Search, Filter, Download, Loader2, ChevronLeft, ChevronRight, AlertCircle } from 'lucide-react'
import { auditService } from '../../api/auditService'
import toast from 'react-hot-toast'

interface DataChangeLog {
  id: number
  timestamp: string
  username: string
  action: string
  details: string
  ipAddress: string
  status: string
}

export default function DataChangeLogs() {
  const [searchTerm, setSearchTerm] = useState('')
  const [selectedEntity, setSelectedEntity] = useState('all')
  const [logs, setLogs] = useState<DataChangeLog[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [page, setPage] = useState(0)
  const [totalPages, setTotalPages] = useState(1)
  const [totalElements, setTotalElements] = useState(0)

  const loadLogs = async () => {
    setIsLoading(true)
    try {
      const response = await auditService.getDataChangeLogs(page, 20, searchTerm)
      setLogs(response.content as DataChangeLog[])
      setTotalPages(response.totalPages)
      setTotalElements(response.totalElements)
    } catch (error) {
      console.error('Failed to load data change logs:', error)
      toast.error('Failed to load data change logs')
      setLogs([])
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    loadLogs()
  }, [page])

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
          <h1 className="text-2xl font-bold text-secondary-900">Data Change Logs</h1>
          <p className="text-secondary-600">Track all data modifications in the system</p>
        </div>
        <button className="btn-secondary"><Download className="h-5 w-5 mr-2" />Export</button>
      </div>

      <div className="card card-body">
        <div className="flex flex-col sm:flex-row gap-4">
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-secondary-400" />
            <input type="text" placeholder="Search changes..." value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} className="input pl-10" />
          </div>
          <div className="flex items-center gap-2">
            <Filter className="h-5 w-5 text-secondary-400" />
            <select value={selectedEntity} onChange={(e) => setSelectedEntity(e.target.value)} className="input w-40">
              <option value="all">All Entities</option>
              <option value="User">User</option>
              <option value="Role">Role</option>
              <option value="Settings">Settings</option>
              <option value="Member">Member</option>
              <option value="Loan">Loan</option>
            </select>
          </div>
        </div>
      </div>

      {/* Loading State */}
      {isLoading ? (
        <div className="flex justify-center py-12">
          <Loader2 className="h-8 w-8 animate-spin text-blue-600" />
        </div>
      ) : logs.length === 0 ? (
        <div className="card card-body">
          <div className="flex flex-col items-center justify-center py-12 text-center">
            <AlertCircle className="h-12 w-12 text-secondary-300 mb-4" />
            <h3 className="text-lg font-medium text-secondary-900 mb-2">No Data Change Logs</h3>
            <p className="text-secondary-500 max-w-md">
              There are currently {totalElements} data change record(s) in the system.
              Data changes will appear here when users modify records.
            </p>
          </div>
        </div>
      ) : (
        <div className="card overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-secondary-50 border-b border-secondary-200">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Timestamp</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Action</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Details</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Changed By</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-secondary-100">
                {logs.map(log => (
                  <tr key={log.id} className="hover:bg-secondary-50">
                    <td className="px-6 py-4 text-sm text-secondary-600">{log.timestamp}</td>
                    <td className="px-6 py-4">
                      <span className="badge badge-warning">{log.action}</span>
                    </td>
                    <td className="px-6 py-4 text-sm text-secondary-600 max-w-md">{log.details}</td>
                    <td className="px-6 py-4 text-sm text-secondary-600">{log.username || 'System'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Pagination */}
          <div className="px-6 py-4 border-t border-secondary-200 flex items-center justify-between">
            <p className="text-sm text-secondary-600">
              Showing {logs.length} of {totalElements} records
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
                Page {page + 1} of {Math.max(1, totalPages)}
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
