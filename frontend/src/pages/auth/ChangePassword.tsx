import { useState } from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
import { Building2, Eye, EyeOff, Loader2, Lock } from 'lucide-react'
import toast from 'react-hot-toast'
import { memberAuthService } from '../../api/memberAuthService'

export default function ChangePassword() {
  const [currentPassword, setCurrentPassword] = useState('')
  const [newPassword, setNewPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')
  const [showCurrentPassword, setShowCurrentPassword] = useState(false)
  const [showNewPassword, setShowNewPassword] = useState(false)
  const [isLoading, setIsLoading] = useState(false)
  const navigate = useNavigate()
  const location = useLocation()
  
  const username = location.state?.username || ''
  const isFirstLogin = location.state?.isFirstLogin || false

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    
    if (newPassword !== confirmPassword) {
      toast.error('New passwords do not match')
      return
    }
    
    if (newPassword.length < 8) {
      toast.error('Password must be at least 8 characters')
      return
    }

    if (newPassword === currentPassword) {
      toast.error('New password must be different from current password')
      return
    }

    setIsLoading(true)

    try {
      const response = await memberAuthService.changePassword({
        username,
        currentPassword,
        newPassword
      })

      if (response.success) {
        toast.success('Password changed successfully! Please login with your new password.')
        navigate('/login')
      } else {
        toast.error(response.error || 'Failed to change password')
      }
    } catch (error) {
      toast.error('Current password is incorrect')
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-primary-600 to-primary-800 flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        {/* Logo */}
        <div className="text-center mb-8">
          <div className="inline-flex items-center justify-center w-16 h-16 bg-white rounded-2xl shadow-lg mb-4">
            <Building2 className="h-10 w-10 text-primary-600" />
          </div>
          <h1 className="text-3xl font-bold text-white">TEMCO Admin</h1>
          <p className="text-primary-200 mt-2">Change Your Password</p>
        </div>

        {/* Change Password Card */}
        <div className="bg-white rounded-2xl shadow-xl p-8">
          <div className="flex items-center gap-3 mb-6">
            <div className="p-2 bg-amber-100 rounded-lg">
              <Lock className="h-6 w-6 text-amber-600" />
            </div>
            <div>
              <h2 className="text-xl font-semibold text-secondary-900">
                {isFirstLogin ? 'Set New Password' : 'Change Password'}
              </h2>
              <p className="text-sm text-secondary-500">
                {isFirstLogin 
                  ? 'For security, please change your default password' 
                  : 'Enter your current and new password'}
              </p>
            </div>
          </div>
          
          <form onSubmit={handleSubmit} className="space-y-5">
            <div>
              <label htmlFor="currentPassword" className="label block mb-1.5">
                Current Password {isFirstLogin && <span className="text-secondary-400">(Your NIC)</span>}
              </label>
              <div className="relative">
                <input
                  id="currentPassword"
                  type={showCurrentPassword ? 'text' : 'password'}
                  value={currentPassword}
                  onChange={(e) => setCurrentPassword(e.target.value)}
                  className="input pr-10"
                  placeholder="Enter current password"
                  required
                />
                <button
                  type="button"
                  onClick={() => setShowCurrentPassword(!showCurrentPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-secondary-400 hover:text-secondary-600"
                >
                  {showCurrentPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                </button>
              </div>
            </div>

            <div>
              <label htmlFor="newPassword" className="label block mb-1.5">
                New Password
              </label>
              <div className="relative">
                <input
                  id="newPassword"
                  type={showNewPassword ? 'text' : 'password'}
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                  className="input pr-10"
                  placeholder="Enter new password (min 8 characters)"
                  required
                  minLength={8}
                />
                <button
                  type="button"
                  onClick={() => setShowNewPassword(!showNewPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-secondary-400 hover:text-secondary-600"
                >
                  {showNewPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                </button>
              </div>
            </div>

            <div>
              <label htmlFor="confirmPassword" className="label block mb-1.5">
                Confirm New Password
              </label>
              <input
                id="confirmPassword"
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                className="input"
                placeholder="Confirm new password"
                required
              />
              {confirmPassword && newPassword !== confirmPassword && (
                <p className="text-sm text-red-500 mt-1">Passwords do not match</p>
              )}
            </div>

            <button
              type="submit"
              disabled={isLoading || newPassword !== confirmPassword}
              className="btn-primary w-full h-11"
            >
              {isLoading ? (
                <>
                  <Loader2 className="h-5 w-5 mr-2 animate-spin" />
                  Changing password...
                </>
              ) : (
                'Change Password'
              )}
            </button>

            <button
              type="button"
              onClick={() => navigate('/login')}
              className="w-full text-center text-sm text-secondary-600 hover:text-secondary-800"
            >
              Back to Login
            </button>
          </form>
        </div>

        {/* Footer */}
        <p className="text-center text-primary-200 text-sm mt-6">
          © 2026 TEMCO Bank. All rights reserved.
        </p>
      </div>
    </div>
  )
}
