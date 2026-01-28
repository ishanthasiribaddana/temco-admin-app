import { useState } from 'react'
import { Outlet, Link, useLocation, useNavigate } from 'react-router-dom'
import { useAuthStore } from '../stores/authStore'
import {
  LayoutDashboard,
  Users,
  Shield,
  Key,
  Menu as MenuIcon,
  Settings,
  FileText,
  History,
  Database,
  UserCog,
  LogOut,
  ChevronDown,
  ChevronRight,
  Bell,
  Search,
  User,
  X,
  Building2,
  Mail,
} from 'lucide-react'
import clsx from 'clsx'

interface NavItem {
  name: string
  href?: string
  icon: React.ElementType
  children?: { name: string; href: string }[]
}

const navigation: NavItem[] = [
  { name: 'Dashboard', href: '/', icon: LayoutDashboard },
  {
    name: 'User Management',
    icon: Users,
    children: [
      { name: 'All Users', href: '/users' },
      { name: 'Create User', href: '/users/create' },
    ],
  },
  {
    name: 'Access Control',
    icon: Shield,
    children: [
      { name: 'Roles', href: '/roles' },
      { name: 'Permissions', href: '/permissions' },
      { name: 'Menu Management', href: '/menus' },
    ],
  },
  {
    name: 'Audit & Logs',
    icon: History,
    children: [
      { name: 'Activity Logs', href: '/audit/activity' },
      { name: 'Data Changes', href: '/audit/data-changes' },
    ],
  },
  {
    name: 'Reference Data',
    icon: Database,
    children: [
      { name: 'Geography', href: '/reference/geography' },
      { name: 'Banks', href: '/reference/banks' },
      { name: 'Organization', href: '/reference/organization' },
      { name: 'General', href: '/reference/general' },
    ],
  },
  { name: 'Email', href: '/email', icon: Mail },
  { name: 'Settings', href: '/settings', icon: Settings },
  { name: 'Impersonation', href: '/impersonation', icon: UserCog },
]

export default function AdminLayout() {
  const [sidebarOpen, setSidebarOpen] = useState(true)
  const [expandedItems, setExpandedItems] = useState<string[]>(['User Management', 'Access Control'])
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false)
  const location = useLocation()
  const navigate = useNavigate()
  const { user, logout, stopImpersonation } = useAuthStore()

  const toggleExpanded = (name: string) => {
    setExpandedItems((prev) =>
      prev.includes(name) ? prev.filter((item) => item !== name) : [...prev, name]
    )
  }

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  const isActive = (href: string) => location.pathname === href

  const isParentActive = (children?: { href: string }[]) =>
    children?.some((child) => location.pathname === child.href)

  return (
    <div className="min-h-screen bg-secondary-50">
      {/* Impersonation Banner */}
      {user?.isImpersonating && (
        <div className="bg-amber-500 text-white px-4 py-2 text-center text-sm font-medium">
          <span>You are impersonating: {user.fullName}</span>
          <button
            onClick={stopImpersonation}
            className="ml-4 underline hover:no-underline"
          >
            Stop Impersonation
          </button>
        </div>
      )}

      {/* Mobile menu button */}
      <div className="lg:hidden fixed top-0 left-0 right-0 z-50 bg-white border-b border-secondary-200 px-4 py-3 flex items-center justify-between">
        <button
          onClick={() => setMobileMenuOpen(true)}
          className="p-2 rounded-lg hover:bg-secondary-100"
        >
          <MenuIcon className="h-6 w-6" />
        </button>
        <div className="flex items-center space-x-2">
          <Building2 className="h-8 w-8 text-primary-600" />
          <span className="font-bold text-xl text-primary-600">TEMCO</span>
        </div>
        <div className="w-10" />
      </div>

      {/* Mobile sidebar */}
      {mobileMenuOpen && (
        <div className="lg:hidden fixed inset-0 z-50">
          <div className="fixed inset-0 bg-black/50" onClick={() => setMobileMenuOpen(false)} />
          <div className="fixed inset-y-0 left-0 w-72 bg-white shadow-xl">
            <div className="flex items-center justify-between p-4 border-b">
              <div className="flex items-center space-x-2">
                <Building2 className="h-8 w-8 text-primary-600" />
                <span className="font-bold text-xl text-primary-600">TEMCO Admin</span>
              </div>
              <button onClick={() => setMobileMenuOpen(false)}>
                <X className="h-6 w-6" />
              </button>
            </div>
            <nav className="p-4 space-y-1">
              {navigation.map((item) => (
                <NavItemComponent
                  key={item.name}
                  item={item}
                  expanded={expandedItems.includes(item.name)}
                  onToggle={() => toggleExpanded(item.name)}
                  isActive={isActive}
                  isParentActive={isParentActive}
                  collapsed={false}
                  onNavigate={() => setMobileMenuOpen(false)}
                />
              ))}
            </nav>
          </div>
        </div>
      )}

      {/* Desktop sidebar */}
      <aside
        className={clsx(
          'hidden lg:fixed lg:inset-y-0 lg:flex lg:flex-col bg-white border-r border-secondary-200 transition-all duration-300 z-40',
          sidebarOpen ? 'lg:w-64' : 'lg:w-20'
        )}
      >
        {/* Logo */}
        <div className="flex items-center h-16 px-4 border-b border-secondary-200">
          <Building2 className="h-8 w-8 text-primary-600 flex-shrink-0" />
          {sidebarOpen && (
            <span className="ml-2 font-bold text-xl text-primary-600">TEMCO Admin</span>
          )}
        </div>

        {/* Navigation */}
        <nav className="flex-1 overflow-y-auto p-4 space-y-1">
          {navigation.map((item) => (
            <NavItemComponent
              key={item.name}
              item={item}
              expanded={expandedItems.includes(item.name)}
              onToggle={() => toggleExpanded(item.name)}
              isActive={isActive}
              isParentActive={isParentActive}
              collapsed={!sidebarOpen}
            />
          ))}
        </nav>

        {/* Toggle button */}
        <button
          onClick={() => setSidebarOpen(!sidebarOpen)}
          className="p-4 border-t border-secondary-200 hover:bg-secondary-50 flex items-center justify-center"
        >
          <ChevronRight
            className={clsx('h-5 w-5 text-secondary-500 transition-transform', sidebarOpen && 'rotate-180')}
          />
        </button>
      </aside>

      {/* Main content */}
      <div className={clsx('transition-all duration-300', sidebarOpen ? 'lg:pl-64' : 'lg:pl-20')}>
        {/* Top bar */}
        <header className="hidden lg:flex h-16 bg-white border-b border-secondary-200 items-center justify-between px-6">
          <div className="flex items-center flex-1">
            <div className="relative w-96">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-5 w-5 text-secondary-400" />
              <input
                type="text"
                placeholder="Search..."
                className="input pl-10"
              />
            </div>
          </div>
          <div className="flex items-center space-x-4">
            <button className="p-2 rounded-lg hover:bg-secondary-100 relative">
              <Bell className="h-5 w-5 text-secondary-600" />
              <span className="absolute top-1 right-1 h-2 w-2 bg-red-500 rounded-full" />
            </button>
            <div className="relative group">
              <button className="flex items-center space-x-3 p-2 rounded-lg hover:bg-secondary-100">
                <div className="h-8 w-8 rounded-full bg-primary-100 flex items-center justify-center">
                  <User className="h-5 w-5 text-primary-600" />
                </div>
                <div className="text-left">
                  <p className="text-sm font-medium text-secondary-900">{user?.fullName}</p>
                  <p className="text-xs text-secondary-500">{user?.role}</p>
                </div>
                <ChevronDown className="h-4 w-4 text-secondary-500" />
              </button>
              <div className="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg border border-secondary-200 py-1 hidden group-hover:block">
                <Link
                  to="/profile"
                  className="flex items-center px-4 py-2 text-sm text-secondary-700 hover:bg-secondary-50"
                >
                  <User className="h-4 w-4 mr-2" />
                  Profile
                </Link>
                <Link
                  to="/settings"
                  className="flex items-center px-4 py-2 text-sm text-secondary-700 hover:bg-secondary-50"
                >
                  <Settings className="h-4 w-4 mr-2" />
                  Settings
                </Link>
                <hr className="my-1" />
                <button
                  onClick={handleLogout}
                  className="flex items-center w-full px-4 py-2 text-sm text-red-600 hover:bg-red-50"
                >
                  <LogOut className="h-4 w-4 mr-2" />
                  Logout
                </button>
              </div>
            </div>
          </div>
        </header>

        {/* Page content */}
        <main className="p-6 pt-20 lg:pt-6">
          <Outlet />
        </main>
      </div>
    </div>
  )
}

interface NavItemComponentProps {
  item: NavItem
  expanded: boolean
  onToggle: () => void
  isActive: (href: string) => boolean
  isParentActive: (children?: { href: string }[]) => boolean | undefined
  collapsed: boolean
  onNavigate?: () => void
}

function NavItemComponent({
  item,
  expanded,
  onToggle,
  isActive,
  isParentActive,
  collapsed,
  onNavigate,
}: NavItemComponentProps) {
  const Icon = item.icon

  if (item.children) {
    return (
      <div>
        <button
          onClick={onToggle}
          className={clsx(
            'w-full flex items-center px-3 py-2 rounded-lg text-sm font-medium transition-colors',
            isParentActive(item.children)
              ? 'bg-primary-50 text-primary-700'
              : 'text-secondary-700 hover:bg-secondary-100'
          )}
        >
          <Icon className="h-5 w-5 flex-shrink-0" />
          {!collapsed && (
            <>
              <span className="ml-3 flex-1 text-left">{item.name}</span>
              <ChevronDown
                className={clsx('h-4 w-4 transition-transform', expanded && 'rotate-180')}
              />
            </>
          )}
        </button>
        {!collapsed && expanded && (
          <div className="mt-1 ml-8 space-y-1">
            {item.children.map((child) => (
              <Link
                key={child.href}
                to={child.href}
                onClick={onNavigate}
                className={clsx(
                  'block px-3 py-2 rounded-lg text-sm transition-colors',
                  isActive(child.href)
                    ? 'bg-primary-100 text-primary-700 font-medium'
                    : 'text-secondary-600 hover:bg-secondary-100'
                )}
              >
                {child.name}
              </Link>
            ))}
          </div>
        )}
      </div>
    )
  }

  return (
    <Link
      to={item.href!}
      onClick={onNavigate}
      className={clsx(
        'flex items-center px-3 py-2 rounded-lg text-sm font-medium transition-colors',
        isActive(item.href!)
          ? 'bg-primary-100 text-primary-700'
          : 'text-secondary-700 hover:bg-secondary-100'
      )}
    >
      <Icon className="h-5 w-5 flex-shrink-0" />
      {!collapsed && <span className="ml-3">{item.name}</span>}
    </Link>
  )
}
