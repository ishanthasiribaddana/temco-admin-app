import { useState } from 'react'
import { useAuthStore } from '../../stores/authStore'
import { Save, Loader2, User, Lock, Bell } from 'lucide-react'
import toast from 'react-hot-toast'

export default function Profile() {
  const { user } = useAuthStore()
  const [isLoading, setIsLoading] = useState(false)
  const [activeTab, setActiveTab] = useState('profile')

  const handleSave = async () => {
    setIsLoading(true)
    try {
      await new Promise(resolve => setTimeout(resolve, 1000))
      toast.success('Profile updated successfully')
    } catch (error) {
      toast.error('Failed to update profile')
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-secondary-900">My Profile</h1>
        <p className="text-secondary-600">Manage your account settings</p>
      </div>

      <div className="flex gap-6">
        <div className="w-64 space-y-1">
          {[
            { id: 'profile', name: 'Profile Info', icon: User },
            { id: 'password', name: 'Change Password', icon: Lock },
            { id: 'notifications', name: 'Notifications', icon: Bell },
          ].map(tab => {
            const Icon = tab.icon
            return (
              <button
                key={tab.id}
                onClick={() => setActiveTab(tab.id)}
                className={`w-full flex items-center px-4 py-3 rounded-lg text-left transition-colors ${activeTab === tab.id ? 'bg-primary-50 text-primary-700' : 'hover:bg-secondary-100 text-secondary-700'}`}
              >
                <Icon className="h-5 w-5 mr-3" />
                {tab.name}
              </button>
            )
          })}
        </div>

        <div className="flex-1 card">
          {activeTab === 'profile' && (
            <div className="card-body space-y-6">
              <div className="flex items-center gap-6">
                <div className="h-24 w-24 rounded-full bg-primary-100 flex items-center justify-center">
                  <span className="text-4xl font-bold text-primary-600">{user?.fullName?.split(' ').map(n => n[0]).join('')}</span>
                </div>
                <div>
                  <h3 className="text-xl font-semibold text-secondary-900">{user?.fullName}</h3>
                  <p className="text-secondary-500">@{user?.username}</p>
                  <span className="badge badge-info mt-2">{user?.role}</span>
                </div>
              </div>
              <hr />
              <div className="grid grid-cols-2 gap-6">
                <div>
                  <label className="label block mb-1.5">Full Name</label>
                  <input type="text" className="input" defaultValue={user?.fullName} />
                </div>
                <div>
                  <label className="label block mb-1.5">Email</label>
                  <input type="email" className="input" defaultValue={user?.email} />
                </div>
                <div>
                  <label className="label block mb-1.5">Username</label>
                  <input type="text" className="input" defaultValue={user?.username} disabled />
                </div>
                <div>
                  <label className="label block mb-1.5">Role</label>
                  <input type="text" className="input" defaultValue={user?.role} disabled />
                </div>
              </div>
              <div className="flex justify-end">
                <button onClick={handleSave} className="btn-primary" disabled={isLoading}>
                  {isLoading ? <><Loader2 className="h-5 w-5 mr-2 animate-spin" />Saving...</> : <><Save className="h-5 w-5 mr-2" />Save Changes</>}
                </button>
              </div>
            </div>
          )}

          {activeTab === 'password' && (
            <div className="card-body space-y-6">
              <h3 className="font-semibold text-secondary-900 text-lg">Change Password</h3>
              <div className="max-w-md space-y-4">
                <div>
                  <label className="label block mb-1.5">Current Password</label>
                  <input type="password" className="input" placeholder="Enter current password" />
                </div>
                <div>
                  <label className="label block mb-1.5">New Password</label>
                  <input type="password" className="input" placeholder="Enter new password" />
                </div>
                <div>
                  <label className="label block mb-1.5">Confirm New Password</label>
                  <input type="password" className="input" placeholder="Confirm new password" />
                </div>
              </div>
              <div className="flex justify-end">
                <button onClick={handleSave} className="btn-primary" disabled={isLoading}>
                  {isLoading ? <><Loader2 className="h-5 w-5 mr-2 animate-spin" />Updating...</> : <><Lock className="h-5 w-5 mr-2" />Update Password</>}
                </button>
              </div>
            </div>
          )}

          {activeTab === 'notifications' && (
            <div className="card-body space-y-6">
              <h3 className="font-semibold text-secondary-900 text-lg">Notification Preferences</h3>
              <div className="space-y-4">
                {['Email notifications', 'Browser notifications', 'SMS alerts', 'Weekly digest'].map(item => (
                  <div key={item} className="flex items-center justify-between p-4 bg-secondary-50 rounded-lg">
                    <p className="font-medium text-secondary-900">{item}</p>
                    <label className="relative inline-flex items-center cursor-pointer">
                      <input type="checkbox" className="sr-only peer" defaultChecked />
                      <div className="w-11 h-6 bg-secondary-300 peer-focus:ring-2 peer-focus:ring-primary-300 rounded-full peer peer-checked:after:translate-x-full after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary-600"></div>
                    </label>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}
