import { useState, useEffect } from 'react'
import { useNavigate, useParams, Link } from 'react-router-dom'
import { ArrowLeft, Save, Loader2 } from 'lucide-react'
import toast from 'react-hot-toast'

export default function RoleEdit() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [isLoading, setIsLoading] = useState(false)
  const [isFetching, setIsFetching] = useState(true)
  const [formData, setFormData] = useState({ name: '', description: '' })

  useEffect(() => {
    const fetchRole = async () => {
      await new Promise(resolve => setTimeout(resolve, 500))
      setFormData({ name: 'Manager', description: 'Branch management access' })
      setIsFetching(false)
    }
    fetchRole()
  }, [id])

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setIsLoading(true)
    try {
      await new Promise(resolve => setTimeout(resolve, 1000))
      toast.success('Role updated successfully')
      navigate('/roles')
    } catch (error) {
      toast.error('Failed to update role')
    } finally {
      setIsLoading(false)
    }
  }

  if (isFetching) {
    return (
      <div className="flex items-center justify-center h-64">
        <Loader2 className="h-8 w-8 animate-spin text-primary-600" />
      </div>
    )
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center gap-4">
        <Link to="/roles" className="p-2 hover:bg-secondary-100 rounded-lg">
          <ArrowLeft className="h-5 w-5" />
        </Link>
        <div>
          <h1 className="text-2xl font-bold text-secondary-900">Edit Role</h1>
          <p className="text-secondary-600">Update role information</p>
        </div>
      </div>

      <form onSubmit={handleSubmit} className="card">
        <div className="card-header">
          <h3 className="font-semibold text-secondary-900">Role Information</h3>
        </div>
        <div className="card-body space-y-6">
          <div>
            <label className="label block mb-1.5">Role Name</label>
            <input
              type="text"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              className="input max-w-md"
              required
            />
          </div>
          <div>
            <label className="label block mb-1.5">Description</label>
            <textarea
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              className="input max-w-md h-24 resize-none"
            />
          </div>
        </div>
        <div className="px-6 py-4 border-t border-secondary-200 flex justify-end gap-3">
          <Link to="/roles" className="btn-secondary">Cancel</Link>
          <button type="submit" className="btn-primary" disabled={isLoading}>
            {isLoading ? (
              <><Loader2 className="h-5 w-5 mr-2 animate-spin" />Saving...</>
            ) : (
              <><Save className="h-5 w-5 mr-2" />Save Changes</>
            )}
          </button>
        </div>
      </form>
    </div>
  )
}
