import os
import sys
import shutil

def release(version):
	zzuMod = useSVN('zz.utils', 'http://stgo.dyndns.org/svn/gpothier/devel/zz.utils')
	tpMod = useSVN('tod.plugin', 'http://pleiad.dcc.uchile.cl/svn/tod/tod.plugin/trunk/')
	tpaMod = useSVN('tod.plugin.ajdt', 'http://pleiad.dcc.uchile.cl/svn/tod/tod.plugin.ajdt/')
	todMod = useSVN('TOD', 'http://pleiad.dcc.uchile.cl/svn/tod/core/trunk/')
	toddbgridMod = useSVN('TOD-dbgrid', 'http://pleiad.dcc.uchile.cl/svn/tod/dbgrid/trunk/')
	#zzcMod = useSVN('zz.csg', 'http://stgo.dyndns.org/svn/gpothier/devel/zz.csg/trunk')
	zzeuMod = useSVN('zz.eclipse.utils', 'http://stgo.dyndns.org/svn/gpothier/devel/zz.eclipse.utils/')
	#reflexMod = useSVN('reflex', 'http://reflex.dcc.uchile.cl/svn/base/trunk/')
	#pomMod = useSVN('pom', 'http://reflex.dcc.uchile.cl/svn/plugins/pom/')
	
	
	print 'Building native library...\n'
	cwd = os.getcwd()
	os.chdir(todMod.path + '/src/native')
	
	if os.system('make clean') != 0: 
		print 'Build failed.'
		sys.exit(-1)
		
	if os.system('PLATFORM=linux make') != 0:
		print 'Build failed.'
		sys.exit(-1)
		
	if os.system('PLATFORM=win32 make') != 0:
		print 'Build failed.'
		sys.exit(-1)

	os.chdir(cwd)
	
	print 'Cleaning...\n'
	antBuild('TOD', 'build.xml', 'clean')
	antBuild('TOD-dbgrid', 'build.xml', 'clean')
	antBuild('zz.utils', 'build.xml', 'clean')
	antBuild('zz.eclipse.utils', 'build-plugin.xml', 'clean')
	antBuild('tod.plugin', 'build-plugin.xml', 'clean')
	antBuild('tod.plugin.ajdt', 'build-plugin.xml', 'clean')
	
	print 'Building plugin and dependencies...\n'
	setEclipsePluginVersion('tod.plugin', version)
	antBuild('tod.plugin', 'build-plugin.xml', 'plugin')
	
	setEclipsePluginVersion('tod.plugin.ajdt', version)
	antBuild('tod.plugin.ajdt', 'build-plugin.xml', 'plugin')
	
	print 'Packaging plugins...\n'
	os.mkdir('release/plugins')
	shutil.copytree(tpMod.path + '/build/tod.plugin', 'release/plugins/tod.plugin_' + version)
	shutil.copytree(tpaMod.path + '/build/tod.plugin.ajdt', 'release/plugins/tod.plugin.ajdt_' + version)
	shutil.copytree(zzeuMod.path + '/build/zz.eclipse.utils', 'release/plugins/zz.eclipse.utils_1.0.0')

	os.chdir('release')
	ret = os.system('zip -rm tod.plugin_' + version + '.zip plugins')
	os.chdir(cwd)
	
	print 'Packaging standalone database...\n'
	antBuild('TOD-dbgrid', 'build.xml', 'release-db')
	shutil.copy(toddbgridMod.path + '/build/tod-db.zip', 'release/tod-db_' + version + '.zip')
	shutil.copy(toddbgridMod.path + '/build/tod-db.tar.gz', 'release/tod-db_' + version + '.tar.gz')
	
	