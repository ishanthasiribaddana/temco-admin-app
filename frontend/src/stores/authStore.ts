import { create } from 'zustand'
import { persist } from 'zustand/middleware'

interface User {
  id: number
  username: string
  email: string
  fullName: string
  role: string
  permissions: string[]
  isImpersonating?: boolean
  originalUser?: {
    id: number
    username: string
    fullName: string
  }
}

interface AuthState {
  user: User | null
  token: string | null
  isAuthenticated: boolean
  login: (user: User, token: string) => void
  logout: () => void
  updateUser: (user: Partial<User>) => void
  startImpersonation: (targetUser: User, originalUser: User) => void
  stopImpersonation: () => void
  hasPermission: (permission: string) => boolean
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set, get) => ({
      user: null,
      token: null,
      isAuthenticated: false,

      login: (user, token) => {
        set({ user, token, isAuthenticated: true })
      },

      logout: () => {
        set({ user: null, token: null, isAuthenticated: false })
      },

      updateUser: (userData) => {
        const currentUser = get().user
        if (currentUser) {
          set({ user: { ...currentUser, ...userData } })
        }
      },

      startImpersonation: (targetUser, originalUser) => {
        set({
          user: {
            ...targetUser,
            isImpersonating: true,
            originalUser: {
              id: originalUser.id,
              username: originalUser.username,
              fullName: originalUser.fullName,
            },
          },
        })
      },

      stopImpersonation: () => {
        const currentUser = get().user
        if (currentUser?.originalUser) {
          set({
            user: {
              id: currentUser.originalUser.id,
              username: currentUser.originalUser.username,
              fullName: currentUser.originalUser.fullName,
              email: '',
              role: 'ADMIN',
              permissions: ['*'],
              isImpersonating: false,
            },
          })
        }
      },

      hasPermission: (permission) => {
        const user = get().user
        if (!user) return false
        if (user.permissions.includes('*')) return true
        return user.permissions.includes(permission)
      },
    }),
    {
      name: 'temco-admin-auth',
    }
  )
)
