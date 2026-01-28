import { useState } from 'react'
import { Plus, Edit, Trash2, GripVertical, ChevronRight, ChevronDown, Save } from 'lucide-react'
import toast from 'react-hot-toast'

interface MenuItem { id: number; name: string; icon: string; path: string; children?: MenuItem[] }

const mockMenus: MenuItem[] = [
  { id: 1, name: 'Dashboard', icon: 'LayoutDashboard', path: '/' },
  { id: 2, name: 'User Management', icon: 'Users', path: '/users', children: [
    { id: 21, name: 'All Users', icon: '', path: '/users' },
    { id: 22, name: 'Create User', icon: '', path: '/users/create' },
  ]},
  { id: 3, name: 'Roles & Permissions', icon: 'Shield', path: '/roles', children: [
    { id: 31, name: 'Roles', icon: '', path: '/roles' },
    { id: 32, name: 'Permissions', icon: '', path: '/permissions' },
  ]},
  { id: 4, name: 'Settings', icon: 'Settings', path: '/settings' },
  { id: 5, name: 'Audit Logs', icon: 'History', path: '/audit/activity' },
]

export default function MenuManagement() {
  const [menus, setMenus] = useState(mockMenus)
  const [expanded, setExpanded] = useState<number[]>([2, 3])

  const toggleExpand = (id: number) => {
    setExpanded(prev => prev.includes(id) ? prev.filter(i => i !== id) : [...prev, id])
  }

  const handleSave = () => {
    toast.success('Menu configuration saved')
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Menu Management</h1>
          <p className="text-secondary-600">Configure navigation menu structure</p>
        </div>
        <div className="flex gap-3">
          <button className="btn-secondary"><Plus className="h-5 w-5 mr-2" />Add Menu Item</button>
          <button onClick={handleSave} className="btn-primary"><Save className="h-5 w-5 mr-2" />Save Changes</button>
        </div>
      </div>

      <div className="card">
        <div className="card-header"><h3 className="font-semibold text-secondary-900">Menu Structure</h3></div>
        <div className="divide-y divide-secondary-100">
          {menus.map(item => (
            <div key={item.id}>
              <div className="flex items-center px-6 py-3 hover:bg-secondary-50">
                <GripVertical className="h-5 w-5 text-secondary-400 mr-3 cursor-move" />
                {item.children ? (
                  <button onClick={() => toggleExpand(item.id)} className="mr-2">
                    {expanded.includes(item.id) ? <ChevronDown className="h-5 w-5 text-secondary-500" /> : <ChevronRight className="h-5 w-5 text-secondary-500" />}
                  </button>
                ) : <span className="w-7" />}
                <div className="flex-1">
                  <p className="font-medium text-secondary-900">{item.name}</p>
                  <p className="text-sm text-secondary-500">{item.path}</p>
                </div>
                <div className="flex gap-2">
                  <button className="p-2 hover:bg-secondary-100 rounded-lg"><Edit className="h-4 w-4 text-secondary-500" /></button>
                  <button className="p-2 hover:bg-red-50 rounded-lg"><Trash2 className="h-4 w-4 text-red-500" /></button>
                </div>
              </div>
              {item.children && expanded.includes(item.id) && (
                <div className="bg-secondary-50">
                  {item.children.map(child => (
                    <div key={child.id} className="flex items-center px-6 py-3 pl-16 hover:bg-secondary-100">
                      <GripVertical className="h-5 w-5 text-secondary-400 mr-3 cursor-move" />
                      <div className="flex-1">
                        <p className="font-medium text-secondary-700">{child.name}</p>
                        <p className="text-sm text-secondary-500">{child.path}</p>
                      </div>
                      <div className="flex gap-2">
                        <button className="p-2 hover:bg-white rounded-lg"><Edit className="h-4 w-4 text-secondary-500" /></button>
                        <button className="p-2 hover:bg-red-50 rounded-lg"><Trash2 className="h-4 w-4 text-red-500" /></button>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
