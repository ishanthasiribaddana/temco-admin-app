import React from 'react';
import { 
  Terminal, 
  GitBranch, 
  Tag, 
  Server, 
  CheckCircle, 
  AlertTriangle,
  ArrowLeft,
  Copy,
  ExternalLink,
  RefreshCw
} from 'lucide-react';
import { Link } from 'react-router-dom';
import { toast } from 'react-hot-toast';

const CICDGuide: React.FC = () => {
  const copyToClipboard = (text: string) => {
    navigator.clipboard.writeText(text);
    toast.success('Copied to clipboard!');
  };

  return (
    <div className="p-6 max-w-6xl mx-auto">
      {/* Back Button */}
      <Link 
        to="/developer-guide" 
        className="inline-flex items-center text-primary-600 hover:text-primary-700 mb-6"
      >
        <ArrowLeft className="h-4 w-4 mr-2" />
        Back to Developer Guide
      </Link>

      {/* Header */}
      <div className="mb-8">
        <div className="flex items-center gap-3 mb-2">
          <div className="p-3 bg-teal-500 rounded-lg text-white">
            <Terminal className="h-6 w-6" />
          </div>
          <h1 className="text-2xl font-bold text-secondary-900">AdminApp CI/CD Run Sheet</h1>
        </div>
        <p className="text-secondary-500">
          Quick reference commands for deployment and version management
        </p>
      </div>

      {/* Quick Commands */}
      <div className="bg-white rounded-xl shadow-sm border border-secondary-200 p-6 mb-6">
        <h2 className="text-lg font-semibold text-secondary-900 mb-4 flex items-center gap-2">
          <Terminal className="h-5 w-5 text-teal-500" />
          Quick Reference Commands
        </h2>
        
        <div className="space-y-4">
          <div>
            <h3 className="font-medium text-secondary-700 mb-2">1. Daily Development Workflow</h3>
            <div className="bg-secondary-900 rounded-lg p-4 font-mono text-sm text-green-400 relative">
              <button 
                onClick={() => copyToClipboard('git pull origin main-new\n# Make your code changes...\ngit add .\ngit commit -m "feat: your feature description"\ngit push origin main-new')}
                className="absolute top-2 right-2 p-2 hover:bg-secondary-700 rounded"
              >
                <Copy className="h-4 w-4 text-secondary-400" />
              </button>
              <pre className="whitespace-pre-wrap">{`# Pull latest changes
git pull origin main-new

# Make your code changes...

# Stage and commit
git add .
git commit -m "feat: your feature description"

# Push to remote
git push origin main-new`}</pre>
            </div>
          </div>

          <div>
            <h3 className="font-medium text-secondary-700 mb-2">2. Deploy to Production (Tag-Based)</h3>
            <div className="bg-secondary-900 rounded-lg p-4 font-mono text-sm text-green-400 relative">
              <button 
                onClick={() => copyToClipboard('git tag v1.0.X\ngit push origin v1.0.X')}
                className="absolute top-2 right-2 p-2 hover:bg-secondary-700 rounded"
              >
                <Copy className="h-4 w-4 text-secondary-400" />
              </button>
              <pre>{`# Create version tag
git tag v1.0.X

# Push tag to trigger deployment
git push origin v1.0.X`}</pre>
            </div>
          </div>

          <div>
            <h3 className="font-medium text-secondary-700 mb-2">3. Watch Deployment</h3>
            <a 
              href="https://github.com/ishanthasiribaddana/temco-admin-app/actions" 
              target="_blank" 
              rel="noopener noreferrer"
              className="inline-flex items-center gap-2 text-primary-600 hover:text-primary-700"
            >
              <ExternalLink className="h-4 w-4" />
              https://github.com/ishanthasiribaddana/temco-admin-app/actions
            </a>
          </div>
        </div>
      </div>

      {/* Version Tagging Convention */}
      <div className="bg-white rounded-xl shadow-sm border border-secondary-200 p-6 mb-6">
        <h2 className="text-lg font-semibold text-secondary-900 mb-4 flex items-center gap-2">
          <Tag className="h-5 w-5 text-orange-500" />
          Version Tagging Convention
        </h2>
        
        <div className="overflow-x-auto mb-4">
          <table className="w-full text-sm">
            <thead>
              <tr className="border-b border-secondary-200">
                <th className="text-left py-2 px-4 font-medium text-secondary-700">Tag Format</th>
                <th className="text-left py-2 px-4 font-medium text-secondary-700">Use Case</th>
              </tr>
            </thead>
            <tbody>
              <tr className="border-b border-secondary-100">
                <td className="py-2 px-4 font-mono text-teal-600">v1.0.x</td>
                <td className="py-2 px-4 text-secondary-600">Bug fixes, minor changes</td>
              </tr>
              <tr className="border-b border-secondary-100">
                <td className="py-2 px-4 font-mono text-teal-600">v1.x.0</td>
                <td className="py-2 px-4 text-secondary-600">New features</td>
              </tr>
              <tr>
                <td className="py-2 px-4 font-mono text-teal-600">vx.0.0</td>
                <td className="py-2 px-4 text-secondary-600">Major releases</td>
              </tr>
            </tbody>
          </table>
        </div>

        <div className="bg-secondary-900 rounded-lg p-4 font-mono text-sm text-green-400">
          <pre>{`git tag v1.0.5    # Bug fix
git tag v1.1.0    # New feature
git tag v2.0.0    # Major release`}</pre>
        </div>
      </div>

      {/* Full Deployment Steps */}
      <div className="bg-white rounded-xl shadow-sm border border-secondary-200 p-6 mb-6">
        <h2 className="text-lg font-semibold text-secondary-900 mb-4 flex items-center gap-2">
          <Server className="h-5 w-5 text-blue-500" />
          Full Deployment Steps
        </h2>
        
        <div className="space-y-6">
          <div className="border-l-4 border-blue-500 pl-4">
            <h3 className="font-medium text-secondary-900">Step 1: Ensure Clean State</h3>
            <div className="bg-secondary-900 rounded-lg p-3 font-mono text-sm text-green-400 mt-2">
              <pre>{`git status
git pull origin main-new`}</pre>
            </div>
          </div>

          <div className="border-l-4 border-blue-500 pl-4">
            <h3 className="font-medium text-secondary-900">Step 2: Build Locally (Optional Test)</h3>
            <div className="bg-secondary-900 rounded-lg p-3 font-mono text-sm text-green-400 mt-2">
              <pre>{`# Backend
cd Backend
mvn clean package -DskipTests

# Frontend  
cd ../frontend
npm install
npm run build`}</pre>
            </div>
          </div>

          <div className="border-l-4 border-blue-500 pl-4">
            <h3 className="font-medium text-secondary-900">Step 3: Commit Changes</h3>
            <div className="bg-secondary-900 rounded-lg p-3 font-mono text-sm text-green-400 mt-2">
              <pre>{`git add .
git commit -m "type: description"`}</pre>
            </div>
            <div className="mt-2 text-sm text-secondary-600">
              <strong>Commit Types:</strong> feat: (new feature), fix: (bug fix), refactor:, docs:, style:
            </div>
          </div>

          <div className="border-l-4 border-blue-500 pl-4">
            <h3 className="font-medium text-secondary-900">Step 4: Push and Tag</h3>
            <div className="bg-secondary-900 rounded-lg p-3 font-mono text-sm text-green-400 mt-2">
              <pre>{`git push origin main-new
git tag v1.0.X
git push origin v1.0.X`}</pre>
            </div>
          </div>

          <div className="border-l-4 border-blue-500 pl-4">
            <h3 className="font-medium text-secondary-900">Step 5: Monitor Deployment</h3>
            <ol className="list-decimal list-inside text-sm text-secondary-600 mt-2 space-y-1">
              <li>Go to GitHub Actions</li>
              <li>Click latest workflow run</li>
              <li>Wait for all jobs to complete (✅)</li>
            </ol>
          </div>

          <div className="border-l-4 border-blue-500 pl-4">
            <h3 className="font-medium text-secondary-900">Step 6: Verify Production</h3>
            <div className="bg-secondary-900 rounded-lg p-3 font-mono text-sm text-green-400 mt-2">
              <pre>{`curl -I https://adminpanel.temcobank.com`}</pre>
            </div>
          </div>
        </div>
      </div>

      {/* Rollback Procedure */}
      <div className="bg-white rounded-xl shadow-sm border border-secondary-200 p-6 mb-6">
        <h2 className="text-lg font-semibold text-secondary-900 mb-4 flex items-center gap-2">
          <RefreshCw className="h-5 w-5 text-red-500" />
          Rollback Procedure
        </h2>
        
        <div className="grid md:grid-cols-2 gap-4">
          <div className="border border-secondary-200 rounded-lg p-4">
            <h3 className="font-medium text-secondary-900 mb-2">Option 1: Redeploy Previous Version</h3>
            <div className="bg-secondary-900 rounded-lg p-3 font-mono text-sm text-green-400">
              <pre>{`# List tags
git tag -l

# Checkout previous version
git checkout v1.0.3

# Create new tag from old code
git tag v1.0.X-rollback
git push origin v1.0.X-rollback`}</pre>
            </div>
          </div>

          <div className="border border-secondary-200 rounded-lg p-4">
            <h3 className="font-medium text-secondary-900 mb-2">Option 2: Manual Server Rollback</h3>
            <div className="bg-secondary-900 rounded-lg p-3 font-mono text-sm text-green-400">
              <pre>{`ssh temco-prod
cd /root/adminapp-isolated
# Restore previous WAR/frontend
docker restart admin-wildfly admin-frontend`}</pre>
            </div>
          </div>
        </div>
      </div>

      {/* Troubleshooting */}
      <div className="bg-white rounded-xl shadow-sm border border-secondary-200 p-6 mb-6">
        <h2 className="text-lg font-semibold text-secondary-900 mb-4 flex items-center gap-2">
          <AlertTriangle className="h-5 w-5 text-amber-500" />
          Troubleshooting
        </h2>
        
        <div className="space-y-4">
          <div className="border-l-4 border-amber-500 pl-4">
            <h3 className="font-medium text-secondary-900">Pipeline Failed at Build</h3>
            <ul className="text-sm text-secondary-600 list-disc list-inside mt-1">
              <li>Check Maven/npm errors in Actions log</li>
              <li>Verify dependencies in pom.xml / package.json</li>
            </ul>
          </div>

          <div className="border-l-4 border-amber-500 pl-4">
            <h3 className="font-medium text-secondary-900">Pipeline Failed at Deploy</h3>
            <ul className="text-sm text-secondary-600 list-disc list-inside mt-1">
              <li>Verify GitHub Secrets are correct</li>
              <li>Check server SSH connectivity</li>
              <li>Verify Docker containers are running on server</li>
            </ul>
          </div>

          <div className="border-l-4 border-amber-500 pl-4">
            <h3 className="font-medium text-secondary-900">Check Server Status</h3>
            <div className="bg-secondary-900 rounded-lg p-3 font-mono text-sm text-green-400 mt-2">
              <pre>{`ssh temco-prod "docker ps | grep admin"
ssh temco-prod "curl -s localhost:8085/api/health"`}</pre>
            </div>
          </div>
        </div>
      </div>

      {/* GitHub Secrets Reference */}
      <div className="bg-white rounded-xl shadow-sm border border-secondary-200 p-6 mb-6">
        <h2 className="text-lg font-semibold text-secondary-900 mb-4 flex items-center gap-2">
          <GitBranch className="h-5 w-5 text-purple-500" />
          GitHub Secrets Reference
        </h2>
        
        <div className="overflow-x-auto">
          <table className="w-full text-sm">
            <thead>
              <tr className="border-b border-secondary-200">
                <th className="text-left py-2 px-4 font-medium text-secondary-700">Secret</th>
                <th className="text-left py-2 px-4 font-medium text-secondary-700">Value</th>
              </tr>
            </thead>
            <tbody>
              <tr className="border-b border-secondary-100">
                <td className="py-2 px-4 font-mono text-purple-600">SSH_PRIVATE_KEY</td>
                <td className="py-2 px-4 text-secondary-600">Content of ~/.ssh/id_ed25519_temco</td>
              </tr>
              <tr className="border-b border-secondary-100">
                <td className="py-2 px-4 font-mono text-purple-600">SERVER_HOST</td>
                <td className="py-2 px-4 text-secondary-600">109.123.227.166</td>
              </tr>
              <tr>
                <td className="py-2 px-4 font-mono text-purple-600">SERVER_USER</td>
                <td className="py-2 px-4 text-secondary-600">root</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      {/* CI/CD Pipeline Flow */}
      <div className="bg-white rounded-xl shadow-sm border border-secondary-200 p-6 mb-6">
        <h2 className="text-lg font-semibold text-secondary-900 mb-4 flex items-center gap-2">
          <CheckCircle className="h-5 w-5 text-green-500" />
          CI/CD Pipeline Flow
        </h2>
        
        <div className="flex flex-wrap items-center justify-center gap-4 py-6">
          <div className="bg-secondary-100 rounded-lg px-4 py-3 text-center">
            <div className="font-mono text-sm text-secondary-700">git push tag</div>
            <div className="text-xs text-secondary-500">vX.X.X</div>
          </div>
          <div className="text-secondary-400">→</div>
          <div className="bg-blue-100 rounded-lg px-4 py-3 text-center">
            <div className="font-medium text-blue-700">GitHub Actions</div>
            <div className="text-xs text-blue-500">Triggered</div>
          </div>
          <div className="text-secondary-400">→</div>
          <div className="bg-purple-100 rounded-lg px-4 py-3 text-center">
            <div className="font-medium text-purple-700">Build</div>
            <div className="text-xs text-purple-500">Backend + Frontend</div>
          </div>
          <div className="text-secondary-400">→</div>
          <div className="bg-teal-100 rounded-lg px-4 py-3 text-center">
            <div className="font-medium text-teal-700">Deploy</div>
            <div className="text-xs text-teal-500">via SSH</div>
          </div>
          <div className="text-secondary-400">→</div>
          <div className="bg-green-100 rounded-lg px-4 py-3 text-center">
            <div className="font-medium text-green-700">Production</div>
            <div className="text-xs text-green-500">Server</div>
          </div>
        </div>
      </div>

      {/* Resources */}
      <div className="bg-teal-50 rounded-xl p-6 border border-teal-200">
        <h3 className="font-semibold text-teal-900 mb-3">🔗 Resources</h3>
        <div className="grid md:grid-cols-2 gap-4 text-sm">
          <div>
            <strong className="text-teal-800">GitHub Repo:</strong>
            <a 
              href="https://github.com/ishanthasiribaddana/temco-admin-app" 
              target="_blank" 
              rel="noopener noreferrer"
              className="ml-2 text-teal-600 hover:underline"
            >
              temco-admin-app
            </a>
          </div>
          <div>
            <strong className="text-teal-800">Actions:</strong>
            <a 
              href="https://github.com/ishanthasiribaddana/temco-admin-app/actions" 
              target="_blank" 
              rel="noopener noreferrer"
              className="ml-2 text-teal-600 hover:underline"
            >
              View Workflows
            </a>
          </div>
          <div>
            <strong className="text-teal-800">Production URL:</strong>
            <a 
              href="https://adminpanel.temcobank.com" 
              target="_blank" 
              rel="noopener noreferrer"
              className="ml-2 text-teal-600 hover:underline"
            >
              adminpanel.temcobank.com
            </a>
          </div>
          <div>
            <strong className="text-teal-800">Server IP:</strong>
            <span className="ml-2 text-teal-700 font-mono">109.123.227.166</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CICDGuide;
