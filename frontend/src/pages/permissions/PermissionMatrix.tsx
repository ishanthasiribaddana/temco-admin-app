import { useState } from 'react'
import { Save, Loader2, Check } from 'lucide-react'
import toast from 'react-hot-toast'

const modules = ['Users', 'Roles', 'Settings', 'Audit', 'Reports', 'Members', 'Loans', 'Finance']
const actions = ['View', 'Create', 'Edit', 'Delete']
const roles = ['Admin', 'Manager', 'Staff', 'Viewer']

export default function PermissionMatrix() {
  const [isLoading, setIsLoading] = useState(false)
  const [permissions, setPermissions] = useState<Record<string, Record<string, boolean>>>(() => {
    const initial: Record<string, Record<string, boolean>> = {}
    roles.forEach(role => {
      initial[role] = {}
      modules.forEach(module => {
        actions.forEach(action => {
          const key = `${module}.${action}`
          initial[role][key] = role === 'Admin' || (role === 'Manager' && action !== 'Delete') || 
                              (role === 'Staff' && (action === 'View' || action === 'Create')) ||
                              (role === 'Viewer' && action === 'View')
        })
      })
    })
    return initial
  })

  const togglePermission = (role: string, permission: string) => {
    setPermissions(prev => ({
      ...prev,
      [role]: { ...prev[role], [permission]: !prev[role][permission] }
    }))
  }

  const handleSave = async () => {
    setIsLoading(true)
    try {
      await new Promise(resolve => setTimeout(resolve, 1000))
      toast.success('Permissions saved successfully')
    } catch (error) {
      toast.error('Failed to save permissions')
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Permission Matrix</h1>
          <p className="text-secondary-600">Configure role-based access permissions</p>
        </div>
        <button onClick={handleSave} className="btn-primary" disabled={isLoading}>
          {isLoading ? <><Loader2 className="h-5 w-5 mr-2 animate-spin" />Saving...</> : <><Save className="h-5 w-5 mr-2" />Save Changes</>}
        </button>
      </div>

      <div className="card overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead className="bg-secondary-50 border-b border-secondary-200">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Module / Action</th>
                {roles.map(role => (
                  <th key={role} className="px-6 py-3 text-center text-xs font-semibold text-secondary-600 uppercase">{role}</th>
                ))}
              </tr>
            </thead>
            <tbody className="divide-y divide-secondary-100">
              {modules.map(module => (
                <>
                  <tr key={module} className="bg-secondary-50">
                    <td colSpan={roles.length + 1} className="px-6 py-2 font-semibold text-secondary-700">{module}</td>
                  </tr>
                  {actions.map(action => (
                    <tr key={`${module}-${action}`} className="hover:bg-secondary-50">
                      <td className="px-6 py-3 pl-10 text-sm text-secondary-600">{action}</td>
                      {roles.map(role => {
                        const key = `${module}.${action}`
                        const isChecked = permissions[role]?.[key] ?? false
                        return (
                          <td key={`${role}-${key}`} className="px-6 py-3 text-center">
                            <button
                              onClick={() => togglePermission(role, key)}
                              className={`w-6 h-6 rounded border-2 flex items-center justify-center transition-colors ${
                                isChecked ? 'bg-primary-600 border-primary-600 text-white' : 'border-secondary-300 hover:border-primary-400'
                              }`}
                            >
                              {isChecked && <Check className="h-4 w-4" />}
                            </button>
                          </td>
                        )
                      })}
                    </tr>
                  ))}
                </>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}
