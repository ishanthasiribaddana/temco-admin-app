import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { Plus, Search, Filter, MoreVertical, Edit, Trash2, Eye, UserCheck, UserX, Loader2, ChevronLeft, ChevronRight } from 'lucide-react'
import { userService, User } from '../../api/userService'
import toast from 'react-hot-toast'

const mockUsers = [
  { id: 1, username: 'admin', firstName: 'System', lastName: 'Administrator', fullName: 'System Administrator', email: 'admin@temcobank.com', roleName: 'Admin', isActive: true, lastLoginAt: null },
]

export default function UserList() {
  const [searchTerm, setSearchTerm] = useState('')
  const [selectedStatus, setSelectedStatus] = useState('all')
  const [openMenu, setOpenMenu] = useState<number | null>(null)
  const [users, setUsers] = useState<User[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [useMockData, setUseMockData] = useState(false)
  const [page, setPage] = useState(0)
  const [totalPages, setTotalPages] = useState(1)
  const [totalElements, setTotalElements] = useState(0)

  const loadUsers = async () => {
    setIsLoading(true)
    try {
      const response = await userService.getUsers(page, 20, searchTerm, selectedStatus)
      setUsers(response.content)
      setTotalPages(response.totalPages)
      setTotalElements(response.totalElements)
      setUseMockData(false)
    } catch (error) {
      console.error('Failed to load users:', error)
      setUsers(mockUsers as User[])
      setUseMockData(true)
      toast.error('Backend unavailable - showing mock data')
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    loadUsers()
  }, [page, selectedStatus])

  useEffect(() => {
    const debounce = setTimeout(() => {
      setPage(0)
      loadUsers()
    }, 500)
    return () => clearTimeout(debounce)
  }, [searchTerm])

  const getDisplayName = (user: User) => {
    if (user.fullName) return user.fullName
    if (user.firstName || user.lastName) return `${user.firstName || ''} ${user.lastName || ''}`.trim()
    return user.username
  }

  const getInitials = (user: User) => {
    const name = getDisplayName(user)
    return name.split(' ').map(n => n[0]).join('').substring(0, 2).toUpperCase()
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Users</h1>
          <p className="text-secondary-600">Manage system users and their access</p>
        </div>
        <Link to="/users/create" className="btn-primary">
          <Plus className="h-5 w-5 mr-2" />
          Add User
        </Link>
      </div>

      {/* Filters */}
      <div className="card card-body">
        <div className="flex flex-col sm:flex-row gap-4">
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-secondary-400" />
            <input
              type="text"
              placeholder="Search users..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="input pl-10"
            />
          </div>
          <div className="flex items-center gap-2">
            <Filter className="h-5 w-5 text-secondary-400" />
            <select
              value={selectedStatus}
              onChange={(e) => setSelectedStatus(e.target.value)}
              className="input w-40"
            >
              <option value="all">All Status</option>
              <option value="active">Active</option>
              <option value="inactive">Inactive</option>
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
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase tracking-wider">User</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase tracking-wider">Role</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase tracking-wider">Status</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase tracking-wider">Last Login</th>
                  <th className="px-6 py-3 text-right text-xs font-semibold text-secondary-600 uppercase tracking-wider">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-secondary-100">
                {users.map((user) => (
                  <tr key={user.id} className="hover:bg-secondary-50">
                    <td className="px-6 py-4">
                      <div className="flex items-center">
                        <div className="h-10 w-10 rounded-full bg-primary-100 flex items-center justify-center">
                          <span className="text-primary-700 font-semibold">
                            {getInitials(user)}
                          </span>
                        </div>
                        <div className="ml-4">
                          <p className="font-medium text-secondary-900">{getDisplayName(user)}</p>
                          <p className="text-sm text-secondary-500">{user.email || user.username}</p>
                        </div>
                      </div>
                    </td>
                    <td className="px-6 py-4">
                      <span className="badge badge-info">{user.roleName}</span>
                    </td>
                    <td className="px-6 py-4">
                      <span className={`badge ${user.isActive ? 'badge-success' : 'badge-danger'}`}>
                        {user.isActive ? 'active' : 'inactive'}
                      </span>
                    </td>
                    <td className="px-6 py-4 text-sm text-secondary-600">{user.lastLoginAt || 'Never'}</td>
                    <td className="px-6 py-4 text-right">
                      <div className="relative">
                        <button
                          onClick={() => setOpenMenu(openMenu === user.id ? null : user.id)}
                          className="p-2 hover:bg-secondary-100 rounded-lg"
                        >
                          <MoreVertical className="h-5 w-5 text-secondary-500" />
                        </button>
                        {openMenu === user.id && (
                          <div className="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg border border-secondary-200 py-1 z-10">
                            <Link
                              to={`/users/${user.id}`}
                              className="flex items-center px-4 py-2 text-sm text-secondary-700 hover:bg-secondary-50"
                            >
                              <Eye className="h-4 w-4 mr-2" />
                              View Details
                            </Link>
                            <Link
                              to={`/users/${user.id}/edit`}
                              className="flex items-center px-4 py-2 text-sm text-secondary-700 hover:bg-secondary-50"
                            >
                              <Edit className="h-4 w-4 mr-2" />
                              Edit
                            </Link>
                            <button className="flex items-center w-full px-4 py-2 text-sm text-secondary-700 hover:bg-secondary-50">
                              {user.isActive ? (
                                <>
                                  <UserX className="h-4 w-4 mr-2" />
                                  Deactivate
                                </>
                              ) : (
                                <>
                                  <UserCheck className="h-4 w-4 mr-2" />
                                  Activate
                                </>
                              )}
                            </button>
                            <hr className="my-1" />
                            <button className="flex items-center w-full px-4 py-2 text-sm text-red-600 hover:bg-red-50">
                              <Trash2 className="h-4 w-4 mr-2" />
                              Delete
                            </button>
                          </div>
                        )}
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Pagination */}
          <div className="px-6 py-4 border-t border-secondary-200 flex items-center justify-between">
            <p className="text-sm text-secondary-600">
              Showing <span className="font-medium">{users.length}</span> of <span className="font-medium">{totalElements}</span> users
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
