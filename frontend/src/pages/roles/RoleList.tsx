import { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { Plus, Search, Shield, Edit, Trash2, Users, Loader2, Wallet } from 'lucide-react'
import { roleService, Role } from '../../api/roleService'
import toast from 'react-hot-toast'

const mockRoles = [
  { id: 1, roleCode: 'ADMIN', roleName: 'Admin', description: 'System administrator with full access', userCount: 1, permissionCount: 45, isActive: true },
  { id: 2, roleCode: 'MEMBER', roleName: 'Member', description: 'Basic member access (177 in member table)', userCount: 177, permissionCount: 5, isActive: true },
  { id: 4, roleCode: 'ACCOUNTANT', roleName: 'Accountant', description: 'Financial operations and data entry', userCount: 5, permissionCount: 20, isActive: true },
  { id: 5, roleCode: 'FINANCE_CONTROLLER', roleName: 'Finance Controller', description: 'Financial verification and approvals', userCount: 2, permissionCount: 25, isActive: true },
  { id: 10, roleCode: 'FINANCE_AUDITOR', roleName: 'Finance Auditor', description: 'Financial audit and compliance review', userCount: 1, permissionCount: 22, isActive: true },
  { id: 6, roleCode: 'CHAIRMAN', roleName: 'Chairman', description: 'Executive oversight access', userCount: 0, permissionCount: 30, isActive: true },
  { id: 7, roleCode: 'SUPPLIER', roleName: 'Supplier', description: 'Vendor portal access', userCount: 0, permissionCount: 8, isActive: true },
  { id: 8, roleCode: 'DATA_CONTROLLER', roleName: 'Data Controller', description: 'Data management access', userCount: 0, permissionCount: 15, isActive: true },
  { id: 9, roleCode: 'DEPARTMENT_HEAD', roleName: 'Department Head', description: 'Department management access', userCount: 0, permissionCount: 22, isActive: true },
]

const FINANCE_ROLE_CODES = ['ACCOUNTANT', 'FINANCE_CONTROLLER', 'FINANCE_AUDITOR']

export default function RoleList() {
  const navigate = useNavigate()
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

  const filteredRoles = roles.filter(role => 
    role.roleName.toLowerCase().includes(searchTerm.toLowerCase()) ||
    role.roleCode.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const financeRoles = filteredRoles.filter(role => FINANCE_ROLE_CODES.includes(role.roleCode))
  const otherRoles = filteredRoles.filter(role => !FINANCE_ROLE_CODES.includes(role.roleCode))

  const getFinanceRole = (roleCode: string) => financeRoles.find(r => r.roleCode === roleCode)
  const accountantRole = getFinanceRole('ACCOUNTANT')
  const controllerRole = getFinanceRole('FINANCE_CONTROLLER')
  const auditorRole = getFinanceRole('FINANCE_AUDITOR')

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

      {/* Finance Team Card */}
      {!isLoading && financeRoles.length > 0 && (
        <div 
          className="card border-2 border-emerald-200 bg-gradient-to-br from-emerald-50 to-white hover:shadow-lg transition-shadow cursor-pointer"
          onClick={() => navigate('/roles/finance-team')}
        >
          <div className="card-body block">
            <div className="flex items-start justify-between">
              <div className="flex items-center gap-3">
                <div className="p-3 bg-emerald-100 rounded-xl">
                  <Wallet className="h-6 w-6 text-emerald-600" />
                </div>
                <div>
                  <h2 className="text-lg font-bold text-emerald-800">Finance Team Roles</h2>
                  <p className="text-sm text-emerald-600">Accountant → Finance Controller → Finance Auditor</p>
                </div>
              </div>
              <div className="text-right">
                <span className="badge bg-emerald-100 text-emerald-700 border-emerald-200">
                  {financeRoles.reduce((sum, r) => sum + r.userCount, 0)} total users
                </span>
                <p className="text-xs text-emerald-500 mt-1">Click to manage →</p>
              </div>
            </div>

            <div className="mt-4 grid grid-cols-1 md:grid-cols-3 gap-4">
              <div className="bg-slate-100 rounded-lg p-4 border-2 border-slate-300 relative">
                <div className="flex items-center justify-between mb-2">
                  <div className="flex items-center gap-2">
                    <Shield className="h-5 w-5 text-slate-700" />
                    <h3 className="font-bold text-slate-800">{auditorRole?.roleName || 'FINANCE AUDITOR'}</h3>
                  </div>
                  {auditorRole && (
                    <Link
                      to={`/roles/${auditorRole.id}/edit`}
                      className="p-1.5 hover:bg-slate-200 rounded"
                      onClick={(e) => e.stopPropagation()}
                    >
                      <Edit className="h-3.5 w-3.5 text-slate-700" />
                    </Link>
                  )}
                </div>
                <p className="text-xs text-slate-600 mb-3">{auditorRole?.description || 'Financial audit and compliance review'}</p>
                <ul className="text-xs text-slate-600 space-y-1">
                  <li>• Reviews all actions</li>
                  <li>• Flags issues</li>
                  <li>• Cannot modify data</li>
                  <li>• Complete visibility</li>
                </ul>
                <div className="mt-2 text-center text-slate-400">↓ Reviews</div>
                <div className="mt-3 pt-3 border-t border-slate-300 flex items-center justify-between text-xs">
                  <span className="flex items-center text-slate-600">
                    <Users className="h-3.5 w-3.5 mr-1" />
                    {auditorRole?.userCount ?? 0} users
                  </span>
                  <span className="text-slate-700 font-medium">{auditorRole?.permissionCount ?? 0} perms</span>
                </div>
              </div>

              <div className="bg-blue-100 rounded-lg p-4 border-2 border-blue-300 relative">
                <div className="flex items-center justify-between mb-2">
                  <div className="flex items-center gap-2">
                    <Shield className="h-5 w-5 text-blue-700" />
                    <h3 className="font-bold text-blue-800">{controllerRole?.roleName || 'FINANCE CONTROLLER'}</h3>
                  </div>
                  {controllerRole && (
                    <Link
                      to={`/roles/${controllerRole.id}/edit`}
                      className="p-1.5 hover:bg-blue-200 rounded"
                      onClick={(e) => e.stopPropagation()}
                    >
                      <Edit className="h-3.5 w-3.5 text-blue-700" />
                    </Link>
                  )}
                </div>
                <p className="text-xs text-blue-700/80 mb-3">{controllerRole?.description || 'Financial verification and approvals'}</p>
                <ul className="text-xs text-blue-700 space-y-1">
                  <li>• Approves/Rejects</li>
                  <li>• Final verification</li>
                  <li>• Can override</li>
                  <li>• Closes periods</li>
                </ul>
                <div className="mt-2 text-center text-blue-500">↓ Verifies</div>
                <div className="mt-3 pt-3 border-t border-blue-300 flex items-center justify-between text-xs">
                  <span className="flex items-center text-blue-700/80">
                    <Users className="h-3.5 w-3.5 mr-1" />
                    {controllerRole?.userCount ?? 0} users
                  </span>
                  <span className="text-blue-800 font-medium">{controllerRole?.permissionCount ?? 0} perms</span>
                </div>
              </div>

              <div className="bg-green-100 rounded-lg p-4 border-2 border-green-300 relative">
                <div className="flex items-center justify-between mb-2">
                  <div className="flex items-center gap-2">
                    <Shield className="h-5 w-5 text-green-700" />
                    <h3 className="font-bold text-green-800">{accountantRole?.roleName || 'ACCOUNTANT'}</h3>
                  </div>
                  {accountantRole && (
                    <Link
                      to={`/roles/${accountantRole.id}/edit`}
                      className="p-1.5 hover:bg-green-200 rounded"
                      onClick={(e) => e.stopPropagation()}
                    >
                      <Edit className="h-3.5 w-3.5 text-green-700" />
                    </Link>
                  )}
                </div>
                <p className="text-xs text-green-700/80 mb-3">{accountantRole?.description || 'Financial operations and data entry'}</p>
                <ul className="text-xs text-green-700 space-y-1">
                  <li>• Daily data entry</li>
                  <li>• Initial verification</li>
                  <li>• Reconciliation matching</li>
                  <li>• Report generation</li>
                </ul>
                <div className="mt-3 pt-3 border-t border-green-300 flex items-center justify-between text-xs">
                  <span className="flex items-center text-green-700/80">
                    <Users className="h-3.5 w-3.5 mr-1" />
                    {accountantRole?.userCount ?? 0} users
                  </span>
                  <span className="text-green-800 font-medium">{accountantRole?.permissionCount ?? 0} perms</span>
                </div>
              </div>
            </div>

            <div className="mt-4 pt-4 border-t border-emerald-200">
              <p className="text-xs text-emerald-700">
                <strong>Workflow:</strong> Accountant enters data → Finance Controller verifies → Finance Auditor reviews & approves
              </p>
            </div>
          </div>
        </div>
      )}

      {/* Other Roles Grid */}
      {!isLoading && otherRoles.length > 0 && (
        <>
          <h3 className="text-lg font-semibold text-secondary-700 mt-2">Other Roles</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {otherRoles.map((role) => (
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
          </div>
        </>
      )}
    </div>
  )
}
