import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { Plus, Search, Shield, Edit, Trash2, Users, Loader2 } from 'lucide-react'
import { roleService, Role } from '../../api/roleService'
import toast from 'react-hot-toast'

const mockRoles = [
  { id: 1, roleCode: 'ADMIN', roleName: 'Admin', description: 'System administrator with full access', userCount: 1, permissionCount: 45, isActive: true },
  { id: 2, roleCode: 'MEMBER', roleName: 'Member', description: 'Basic member access (177 in member table)', userCount: 177, permissionCount: 5, isActive: true },
  { id: 4, roleCode: 'ACCOUNTANT', roleName: 'Accountant', description: 'Financial operations access', userCount: 5, permissionCount: 20, isActive: true },
  { id: 5, roleCode: 'FINANCE_CONTROLLER', roleName: 'Finance Controller', description: 'Financial oversight and approvals', userCount: 0, permissionCount: 25, isActive: true },
  { id: 6, roleCode: 'CHAIRMAN', roleName: 'Chairman', description: 'Executive oversight access', userCount: 0, permissionCount: 30, isActive: true },
  { id: 7, roleCode: 'SUPPLIER', roleName: 'Supplier', description: 'Vendor portal access', userCount: 0, permissionCount: 8, isActive: true },
  { id: 8, roleCode: 'DATA_CONTROLLER', roleName: 'Data Controller', description: 'Data management access', userCount: 0, permissionCount: 15, isActive: true },
  { id: 9, roleCode: 'DEPARTMENT_HEAD', roleName: 'Department Head', description: 'Department management access', userCount: 0, permissionCount: 22, isActive: true },
]

export default function RoleList() {
  const [searchTerm, setSearchTerm] = useState('')
  const [roles, setRoles] = useState<Role[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [useMockData, setUseMockData] = useState(false)

  useEffect(() => {
    loadRoles()
  }, [])

  const loadRoles = async () => {
    setIsLoading(true)
    try {
      const response = await roleService.getAll(0, 100, searchTerm || undefined)
      setRoles(response?.content || [])
      setUseMockData(false)
    } catch (error) {
      console.warn('API unavailable, using mock data')
      setRoles(mockRoles as Role[])
      setUseMockData(true)
    } finally {
      setIsLoading(false)
    }
  }

  const handleDelete = async (id: number) => {
    if (!confirm('Are you sure you want to delete this role?')) return
    
    try {
      if (!useMockData) {
        await roleService.delete(id)
      }
      setRoles(roles.filter(r => r.id !== id))
      toast.success('Role deleted successfully')
    } catch (error) {
      toast.error('Failed to delete role')
    }
  }

  const filteredRoles = roles.filter((role) =>
    role.roleName.toLowerCase().includes(searchTerm.toLowerCase())
  )

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Roles</h1>
          <p className="text-secondary-600">Manage user roles and their permissions</p>
        </div>
        <Link to="/roles/create" className="btn-primary">
          <Plus className="h-5 w-5 mr-2" />
          Add Role
        </Link>
      </div>

      {/* Search */}
      <div className="card card-body">
        <div className="relative max-w-md">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-secondary-400" />
          <input
            type="text"
            placeholder="Search roles..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="input pl-10"
          />
        </div>
      </div>

      {/* Loading State */}
      {isLoading && (
        <div className="flex justify-center py-12">
          <Loader2 className="h-8 w-8 animate-spin text-blue-600" />
        </div>
      )}

      {/* Mock Data Banner */}
      {useMockData && !isLoading && (
        <div className="bg-yellow-50 border border-yellow-200 rounded-lg p-3 text-yellow-800 text-sm">
          ⚠️ Backend unavailable - showing mock data
        </div>
      )}

      {/* Roles Grid */}
      {!isLoading && <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {filteredRoles.map((role) => (
          <div key={role.id} className="card">
            <div className="card-body">
              <div className="flex items-start justify-between">
                <div className="p-3 bg-primary-100 rounded-xl">
                  <Shield className="h-6 w-6 text-primary-600" />
                </div>
                <div className="flex gap-1">
                  <Link
                    to={`/roles/${role.id}/edit`}
                    className="p-2 hover:bg-secondary-100 rounded-lg"
                  >
                    <Edit className="h-4 w-4 text-secondary-500" />
                  </Link>
                  <button onClick={() => handleDelete(role.id)} className="p-2 hover:bg-red-50 rounded-lg">
                    <Trash2 className="h-4 w-4 text-red-500" />
                  </button>
                </div>
              </div>
              <div className="mt-4">
                <h3 className="font-semibold text-secondary-900">{role.roleName}</h3>
                <p className="text-sm text-secondary-500 mt-1">{role.description}</p>
              </div>
              <div className="mt-4 pt-4 border-t border-secondary-100 flex items-center justify-between">
                <div className="flex items-center text-sm text-secondary-600">
                  <Users className="h-4 w-4 mr-1" />
                  {role.userCount} users
                </div>
                <span className="badge badge-info">{role.permissionCount} permissions</span>
              </div>
            </div>
          </div>
        ))}
      </div>}
    </div>
  )
}
