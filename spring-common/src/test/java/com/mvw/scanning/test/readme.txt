#想法
用ResourcePatternResolver从配置的classpath中加载Resource；
分别处理扫描到的每个Resource，用MetadataReaderFactory生成对应的MetadataReader；
根据MetadataReader判断Resource是否是符合条件的Component；
如果是，则生成对应的ScannedGenericBeanDefinition；不是则跳过；
对于经过上面4步生成的符合条件的ScannedGenericBeanDefinition，先用ScopeMetadataResolver
生成ScopeMetadata，将得到的Scope设置到对应的ScannedGenericBeanDefinition中； 



/*
 * 将找到的一个DAO接口注册到Spring容器中
 */
private void registerDAODefinition(ConfigurableListableBeanFactory beanFactory,
        BeanDefinition beanDefinition) {
    final String daoClassName = beanDefinition.getBeanClassName();
    MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
    /*
     * 属性及其设置要按 JadeFactoryBean 的要求来办
     */
    propertyValues.addPropertyValue("objectType", daoClassName);
    propertyValues.addPropertyValue("dataAccessFactory", getDataAccessFactory(beanFactory));
    propertyValues.addPropertyValue("rowMapperFactory", getRowMapperFactory());
    propertyValues.addPropertyValue("interpreterFactory", getInterpreterFactory(beanFactory));
    String cacheProviderName = getCacheProviderName(beanFactory);
    if (cacheProviderName != null) {
        RuntimeBeanReference beanRef = new RuntimeBeanReference(cacheProviderName);
        propertyValues.addPropertyValue("cacheProvider", beanRef);
    }
    String statementWrapperProvider = getStatementWrapperProvider(beanFactory);
    if (statementWrapperProvider != null) {
        RuntimeBeanReference beanRef = new RuntimeBeanReference(statementWrapperProvider);
        propertyValues.addPropertyValue("statementWrapperProvider", beanRef);
    }
    ScannedGenericBeanDefinition scannedBeanDefinition = (ScannedGenericBeanDefinition) beanDefinition;
    scannedBeanDefinition.setPropertyValues(propertyValues);
    scannedBeanDefinition.setBeanClass(JadeFactoryBean.class);

    DefaultListableBeanFactory defaultBeanFactory = (DefaultListableBeanFactory) beanFactory;
    defaultBeanFactory.registerBeanDefinition(daoClassName, beanDefinition);

    if (logger.isDebugEnabled()) {
        logger.debug("[jade] register DAO: " + daoClassName);
    }
}



