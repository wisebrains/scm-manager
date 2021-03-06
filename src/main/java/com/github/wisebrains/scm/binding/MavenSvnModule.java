package com.github.wisebrains.scm.binding;

import com.github.wisebrains.scm.ScmRepositoryManager;
import com.github.wisebrains.scm.binding.annotation.SvnManager;
import com.github.wisebrains.scm.build.recovery.BuildRecovery;
import com.github.wisebrains.scm.build.recovery.impl.FileLogBasedRecovery;
import com.github.wisebrains.scm.svn.SvnScmManager;
import com.github.wisebrains.util.resolver.MavenProjectDirectoryResolver;
import com.github.wisebrains.util.resolver.SvnRepositoryUrlResolver;
import com.github.wisebrains.util.resolver.impl.DefaultMavenProjectDirectoryResolver;
import com.github.wisebrains.util.resolver.impl.PropertiesBasedSvnRepositoryUrlResolver;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Module configuration providing instance binding for the major application components.
 *
 * @author Marwen Trabesli
 * @since 0.0.1
 */
public class MavenSvnModule extends AbstractModule {

  @Override
  protected void configure() {
    /*
    bind the svn repository manager
     */
    bind( ScmRepositoryManager.class ).annotatedWith( SvnManager.class ).to( SvnScmManager.class );
    /*
    bind the svn url resolver
     */
    bind( SvnRepositoryUrlResolver.class ).to( PropertiesBasedSvnRepositoryUrlResolver.class );
    /*
    bind the maven repository resolver
     */
    bind( MavenProjectDirectoryResolver.class ).to( DefaultMavenProjectDirectoryResolver.class );
    /*
    bind the recovery strategy for build failures
     */
    bind( BuildRecovery.class ).to( FileLogBasedRecovery.class );
  }

  @Provides
  @Singleton
  Config provideConfiguration() {
    return ConfigFactory.load();
  }
}
