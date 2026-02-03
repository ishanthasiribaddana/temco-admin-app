import React from 'react';
import { 
  BookOpen, 
  Code, 
  Database, 
  GitBranch, 
  Layers, 
  Shield, 
  Terminal,
  FileCode,
  ExternalLink,
  Wallet
} from 'lucide-react';

interface GuideCard {
  id: string;
  title: string;
  description: string;
  icon: React.ReactNode;
  color: string;
  link?: string;
  isExternal?: boolean;
  comingSoon?: boolean;
}

const guideCards: GuideCard[] = [
  {
    id: 'customer-supplier-api',
    title: 'Customer-Supplier API Architecture',
    description: 'Learn about our modular architecture pattern where modules own their tables and communicate via internal APIs.',
    icon: <Layers className="h-8 w-8" />,
    color: 'bg-blue-500',
    link: '/docs/customer-supplier-api-training.html',
    isExternal: true
  },
  {
    id: 'finance-team-setup',
    title: 'Finance Team Setup Guide',
    description: 'Complete reference for configuring finance team roles (Accountant, Finance Controller, Auditor) with 76 tasks across 9 categories.',
    icon: <Wallet className="h-8 w-8" />,
    color: 'bg-emerald-500',
    link: '/docs/finance-team-setup-guide.html',
    isExternal: true
  },
  {
    id: 'database-design',
    title: 'Database Design Guidelines',
    description: 'Best practices for table design, naming conventions, and referential integrity in TEMCO system.',
    icon: <Database className="h-8 w-8" />,
    color: 'bg-green-500',
    comingSoon: true
  },
  {
    id: 'api-development',
    title: 'REST API Development',
    description: 'Standards for creating RESTful APIs including naming, versioning, error handling, and documentation.',
    icon: <Code className="h-8 w-8" />,
    color: 'bg-purple-500',
    comingSoon: true
  },
  {
    id: 'security-guide',
    title: 'Security Best Practices',
    description: 'Authentication, authorization, data encryption, and secure coding practices for banking applications.',
    icon: <Shield className="h-8 w-8" />,
    color: 'bg-red-500',
    comingSoon: true
  },
  {
    id: 'git-workflow',
    title: 'Git Workflow & Branching',
    description: 'Our Git branching strategy, commit conventions, pull request process, and code review guidelines.',
    icon: <GitBranch className="h-8 w-8" />,
    color: 'bg-orange-500',
    comingSoon: true
  },
  {
    id: 'coding-standards',
    title: 'Coding Standards',
    description: 'Java and TypeScript coding conventions, code formatting, and best practices for maintainable code.',
    icon: <FileCode className="h-8 w-8" />,
    color: 'bg-indigo-500',
    comingSoon: true
  },
  {
    id: 'deployment-guide',
    title: 'Deployment & CI/CD',
    description: 'Docker containerization, CI/CD pipelines, environment configuration, and release management.',
    icon: <Terminal className="h-8 w-8" />,
    color: 'bg-teal-500',
    link: '/developer-guide/cicd'
  },
  {
    id: 'module-ownership',
    title: 'Module Ownership Reference',
    description: 'Complete list of modules, their owned tables, and responsible teams for the TEMCO system.',
    icon: <BookOpen className="h-8 w-8" />,
    color: 'bg-pink-500',
    comingSoon: true
  }
];

const DeveloperGuide: React.FC = () => {
  
  const handleCardClick = (card: GuideCard) => {
    if (card.comingSoon) {
      return;
    }
    
    if (card.isExternal && card.link) {
      window.open(card.link, '_blank', 'noopener,noreferrer');
    } else if (card.link) {
      window.location.href = card.link;
    }
  };

  return (
    <div className="p-6">
      {/* Header */}
      <div className="mb-8">
        <h1 className="text-2xl font-bold text-secondary-900">Developer Guide</h1>
        <p className="text-secondary-500 mt-1">
          Training materials, coding standards, and architectural documentation for TEMCO developers
        </p>
      </div>

      {/* Stats Summary */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-8">
        <div className="bg-white rounded-xl p-4 shadow-sm border border-secondary-200">
          <div className="text-3xl font-bold text-primary-600">8</div>
          <div className="text-sm text-secondary-500">Guide Topics</div>
        </div>
        <div className="bg-white rounded-xl p-4 shadow-sm border border-secondary-200">
          <div className="text-3xl font-bold text-green-600">3</div>
          <div className="text-sm text-secondary-500">Available Now</div>
        </div>
        <div className="bg-white rounded-xl p-4 shadow-sm border border-secondary-200">
          <div className="text-3xl font-bold text-orange-600">6</div>
          <div className="text-sm text-secondary-500">Coming Soon</div>
        </div>
        <div className="bg-white rounded-xl p-4 shadow-sm border border-secondary-200">
          <div className="text-3xl font-bold text-secondary-600">v1.0</div>
          <div className="text-sm text-secondary-500">Documentation Version</div>
        </div>
      </div>

      {/* Guide Cards Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        {guideCards.map((card) => (
          <div
            key={card.id}
            onClick={() => handleCardClick(card)}
            className={`
              bg-white rounded-xl shadow-sm border border-secondary-200 overflow-hidden
              transition-all duration-200
              ${card.comingSoon 
                ? 'opacity-60 cursor-not-allowed' 
                : 'hover:shadow-lg hover:border-primary-300 cursor-pointer hover:-translate-y-1'
              }
            `}
          >
            {/* Card Header with Icon */}
            <div className={`${card.color} p-4 text-white`}>
              <div className="flex items-center justify-between">
                {card.icon}
                {card.isExternal && !card.comingSoon && (
                  <ExternalLink className="h-4 w-4 opacity-75" />
                )}
                {card.comingSoon && (
                  <span className="text-xs bg-white/20 px-2 py-1 rounded-full">
                    Coming Soon
                  </span>
                )}
              </div>
            </div>
            
            {/* Card Body */}
            <div className="p-4">
              <h3 className="font-semibold text-secondary-900 mb-2">
                {card.title}
              </h3>
              <p className="text-sm text-secondary-500 leading-relaxed">
                {card.description}
              </p>
            </div>
            
            {/* Card Footer */}
            <div className="px-4 pb-4">
              <div className={`
                text-sm font-medium
                ${card.comingSoon ? 'text-secondary-400' : 'text-primary-600'}
              `}>
                {card.comingSoon ? 'Documentation in progress' : 'Click to open →'}
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Additional Info */}
      <div className="mt-8 bg-blue-50 rounded-xl p-6 border border-blue-200">
        <h3 className="font-semibold text-blue-900 mb-2">📚 About Developer Documentation</h3>
        <p className="text-blue-700 text-sm leading-relaxed">
          This documentation is maintained by the TEMCO Architecture Team. Each guide is designed to help 
          developers understand our coding standards, architectural decisions, and best practices. 
          New guides are being added regularly. If you have suggestions for new topics, please contact 
          the Architecture Team.
        </p>
      </div>
    </div>
  );
};

export default DeveloperGuide;
