import { useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import { Plus, Search, Edit, Trash2, MapPin, Building, Globe, List } from 'lucide-react'

const categories = [
  { id: 'geography', name: 'Geography', icon: MapPin, items: ['Countries', 'Provinces', 'Districts', 'Cities', 'DS Divisions', 'GN Divisions'] },
  { id: 'banks', name: 'Banks', icon: Building, items: ['Banks', 'SWIFT Codes', 'Account Types'] },
  { id: 'organization', name: 'Organization', icon: Globe, items: ['Org Categories', 'Org Types', 'Registration Types', 'Business Sectors'] },
  { id: 'general', name: 'General', icon: List, items: ['Gender', 'Relationships', 'Education Levels', 'Professions', 'Membership Levels', 'Status Types'] },
]

const mockData: Record<string, { id: number; name: string; code?: string; status: string }[]> = {
  Countries: [{ id: 1, name: 'Sri Lanka', code: 'LK', status: 'active' }, { id: 2, name: 'India', code: 'IN', status: 'active' }],
  Provinces: [{ id: 1, name: 'Western', code: 'WP', status: 'active' }, { id: 2, name: 'Central', code: 'CP', status: 'active' }],
  Banks: [{ id: 1, name: 'Bank of Ceylon', code: 'BOC', status: 'active' }, { id: 2, name: "People's Bank", code: 'PB', status: 'active' }],
  Gender: [{ id: 1, name: 'Male', status: 'active' }, { id: 2, name: 'Female', status: 'active' }, { id: 3, name: 'Other', status: 'active' }],
}

export default function ReferenceData() {
  const { category } = useParams()
  const [selectedItem, setSelectedItem] = useState<string | null>(null)
  const [searchTerm, setSearchTerm] = useState('')

  const currentCategory = categories.find(c => c.id === category) || categories[0]
  const Icon = currentCategory.icon
  const data = mockData[selectedItem || ''] || []

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-secondary-900">Reference Data</h1>
        <p className="text-secondary-600">Manage master data and lookup tables</p>
      </div>

      <div className="flex gap-6">
        <div className="w-64 space-y-4">
          <div className="card card-body">
            <h3 className="font-semibold text-secondary-900 mb-3">Categories</h3>
            <div className="space-y-1">
              {categories.map(cat => {
                const CatIcon = cat.icon
                return (
                  <Link
                    key={cat.id}
                    to={`/reference/${cat.id}`}
                    className={`flex items-center px-3 py-2 rounded-lg text-sm transition-colors ${category === cat.id ? 'bg-primary-50 text-primary-700' : 'hover:bg-secondary-100 text-secondary-700'}`}
                  >
                    <CatIcon className="h-4 w-4 mr-2" />
                    {cat.name}
                  </Link>
                )
              })}
            </div>
          </div>

          <div className="card card-body">
            <h3 className="font-semibold text-secondary-900 mb-3 flex items-center">
              <Icon className="h-4 w-4 mr-2" />{currentCategory.name}
            </h3>
            <div className="space-y-1">
              {currentCategory.items.map(item => (
                <button
                  key={item}
                  onClick={() => setSelectedItem(item)}
                  className={`w-full text-left px-3 py-2 rounded-lg text-sm transition-colors ${selectedItem === item ? 'bg-primary-100 text-primary-700 font-medium' : 'hover:bg-secondary-100 text-secondary-600'}`}
                >
                  {item}
                </button>
              ))}
            </div>
          </div>
        </div>

        <div className="flex-1">
          {selectedItem ? (
            <div className="card">
              <div className="card-header flex items-center justify-between">
                <h3 className="font-semibold text-secondary-900">{selectedItem}</h3>
                <button className="btn-primary btn-sm"><Plus className="h-4 w-4 mr-1" />Add New</button>
              </div>
              <div className="p-4 border-b border-secondary-200">
                <div className="relative max-w-sm">
                  <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-secondary-400" />
                  <input type="text" placeholder={`Search ${selectedItem.toLowerCase()}...`} value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)} className="input pl-10" />
                </div>
              </div>
              <div className="overflow-x-auto">
                <table className="w-full">
                  <thead className="bg-secondary-50">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Name</th>
                      {data[0]?.code !== undefined && <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Code</th>}
                      <th className="px-6 py-3 text-left text-xs font-semibold text-secondary-600 uppercase">Status</th>
                      <th className="px-6 py-3 text-right text-xs font-semibold text-secondary-600 uppercase">Actions</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-secondary-100">
                    {data.filter(d => d.name.toLowerCase().includes(searchTerm.toLowerCase())).map(item => (
                      <tr key={item.id} className="hover:bg-secondary-50">
                        <td className="px-6 py-4 font-medium text-secondary-900">{item.name}</td>
                        {item.code !== undefined && <td className="px-6 py-4 text-sm text-secondary-600 font-mono">{item.code}</td>}
                        <td className="px-6 py-4"><span className="badge badge-success">{item.status}</span></td>
                        <td className="px-6 py-4 text-right">
                          <button className="p-2 hover:bg-secondary-100 rounded-lg"><Edit className="h-4 w-4 text-secondary-500" /></button>
                          <button className="p-2 hover:bg-red-50 rounded-lg"><Trash2 className="h-4 w-4 text-red-500" /></button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          ) : (
            <div className="card card-body text-center py-16">
              <Icon className="h-12 w-12 text-secondary-300 mx-auto mb-4" />
              <p className="text-secondary-500">Select an item from the left to view and manage data</p>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}
