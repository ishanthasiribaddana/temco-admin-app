import { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuthStore } from '../../stores/authStore'
import { Building2, Eye, EyeOff, Loader2, Info } from 'lucide-react'
import toast from 'react-hot-toast'
import { memberAuthService } from '../../api/memberAuthService'

export default function Login() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [showPassword, setShowPassword] = useState(false)
  const [isLoading, setIsLoading] = useState(false)
  const [nicHint, setNicHint] = useState<string | null>(null)
  const navigate = useNavigate()
  const { login } = useAuthStore()

  // Fetch NIC hint when email is entered
  useEffect(() => {
    const fetchNicHint = async () => {
      if (username && username.includes('@')) {
        try {
          const response = await memberAuthService.getNicHint(username)
          if (response.found) {
            setNicHint(response.hint)
          } else {
            setNicHint(null)
          }
        } catch {
          setNicHint(null)
        }
      } else {
        setNicHint(null)
      }
    }
    
    const debounce = setTimeout(fetchNicHint, 500)
    return () => clearTimeout(debounce)
  }, [username])

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setIsLoading(true)

    try {
      // TODO: Replace with actual API call
      await new Promise((resolve) => setTimeout(resolve, 1000))
      
      // Mock login response
      const mockUser = {
        id: 1,
        username: username,
        email: 'admin@temcobank.com',
        fullName: 'System Administrator',
        role: 'SUPER_ADMIN',
        permissions: ['*'],
      }
      
      login(mockUser, 'mock-jwt-token')
      toast.success('Login successful!')
      navigate('/')
    } catch (error) {
      toast.error('Invalid username or password')
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
          <p className="text-primary-200 mt-2">System Administration Portal</p>
        </div>

        {/* Login Card */}
        <div className="bg-white rounded-2xl shadow-xl p-8">
          <h2 className="text-2xl font-semibold text-secondary-900 mb-6">Welcome back</h2>
          
          <form onSubmit={handleSubmit} className="space-y-5">
            <div>
              <label htmlFor="username" className="label block mb-1.5">
                Email Address
              </label>
              <input
                id="username"
                type="email"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="input"
                placeholder="Enter your email address"
                required
              />
            </div>

            <div>
              <label htmlFor="password" className="label block mb-1.5">
                Password
              </label>
              <div className="relative">
                <input
                  id="password"
                  type={showPassword ? 'text' : 'password'}
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="input pr-10"
                  placeholder={nicHint ? `Enter your NIC (${nicHint})` : "Enter your password"}
                  required
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-secondary-400 hover:text-secondary-600"
                >
                  {showPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                </button>
              </div>
              {nicHint && (
                <div className="flex items-center mt-2 text-sm text-blue-600 bg-blue-50 px-3 py-2 rounded-lg">
                  <Info className="h-4 w-4 mr-2 flex-shrink-0" />
                  <span>First login? Your password is your NIC: <strong>{nicHint}</strong></span>
                </div>
              )}
            </div>

            <div className="flex items-center justify-between">
              <label className="flex items-center">
                <input type="checkbox" className="rounded border-secondary-300 text-primary-600 focus:ring-primary-500" />
                <span className="ml-2 text-sm text-secondary-600">Remember me</span>
              </label>
              <Link to="/forgot-password" className="text-sm text-primary-600 hover:text-primary-700 font-medium">
                Forgot password?
              </Link>
            </div>

            <button
              type="submit"
              disabled={isLoading}
              className="btn-primary w-full h-11"
            >
              {isLoading ? (
                <>
                  <Loader2 className="h-5 w-5 mr-2 animate-spin" />
                  Signing in...
                </>
              ) : (
                'Sign in'
              )}
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
