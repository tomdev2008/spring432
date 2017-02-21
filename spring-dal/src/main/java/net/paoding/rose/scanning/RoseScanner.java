package net.paoding.rose.scanning;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

/**
 * Rose内部定义的扫描工具类<br/>
 * 获取类路径下的classes目录以及那些设置了rose标帜的jar包地址
 */
public class RoseScanner {

    private static SoftReference<RoseScanner> softReference;

    public synchronized static RoseScanner getInstance() {
        if (softReference == null || softReference.get() == null) {
            RoseScanner roseScanner = new RoseScanner();
            softReference = new SoftReference<RoseScanner>(roseScanner);
        }
        return softReference.get();
    }

    // -------------------------------------------------------------
    protected Date createTime = new Date();

    protected ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private List<ResourceRef> classesFolderResources;

    private List<ResourceRef> jarResources;

    // -------------------------------------------------------------

    private RoseScanner() {
    }

    public Date getCreateTime() {
        return createTime;
    }

    // -------------------------------------------------------------
    public List<ResourceRef> getJarOrClassesFolderResources() throws IOException {
        return getJarOrClassesFolderResources(null);
    }

    public List<ResourceRef> getJarOrClassesFolderResources(String[] scope) throws IOException {

        List<ResourceRef> resources;
        if (scope == null) {
            resources = new LinkedList<ResourceRef>();
            resources.addAll(getClassesFolderResources());
            resources.addAll(getJarResources());
        } else if (scope.length == 0) {
            return new ArrayList<ResourceRef>();
        } else {
            resources = new LinkedList<ResourceRef>();
            for (String scopeEntry : scope) {
                String packagePath = scopeEntry.replace('.', '/');
                Resource[] packageResources = resourcePatternResolver.getResources("classpath*:" + packagePath);
                for (Resource pkgResource : packageResources) {
                    String uri = pkgResource.getURI().toString();
                    uri = StringUtils.removeEnd(uri, "/");
                    packagePath = StringUtils.removeEnd(packagePath, "/");
                    uri = StringUtils.removeEnd(uri, packagePath);
                    int beginIndex = uri.lastIndexOf("file:");
                    if (beginIndex == -1) {
                        beginIndex = 0;
                    } else {
                        beginIndex += "file:".length();
                    }
                    int endIndex = uri.lastIndexOf('!');
                    if (endIndex == -1) {
                        endIndex = uri.length();
                    }
                    String path = uri.substring(beginIndex, endIndex);
                    Resource folder = new FileSystemResource(path);
                    ResourceRef ref = ResourceRef.toResourceRef(folder);
                    if (!resources.contains(ref)) {
                        resources.add(ref);
                    }
                }
            }
        }

        return resources;
    }

    /**
     * 将要被扫描的普通类地址(比如WEB-INF/classes或target/classes之类的地址)
     * 
     * @param resourceLoader
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public List<ResourceRef> getClassesFolderResources() throws IOException {
        if (classesFolderResources == null) {
            List<ResourceRef> classesFolderResources = new ArrayList<ResourceRef>();
            Enumeration<URL> founds = resourcePatternResolver.getClassLoader().getResources("");
            while (founds.hasMoreElements()) {
                URL urlObject = founds.nextElement();
                if (!"file".equals(urlObject.getProtocol())) {
                    continue;
                }
                String path = urlObject.getPath();
                Assert.isTrue(path.endsWith("/"));
                File file;
                try {
                    file = new File(urlObject.toURI());
                } catch (URISyntaxException e) {
                    throw new IOException(e);
                }
                if (file.isFile()) {
                    continue;
                }
                Resource resource = new FileSystemResource(file);
                ResourceRef resourceRef = ResourceRef.toResourceRef(resource);
                if (!classesFolderResources.contains(resourceRef)) {
                	 classesFolderResources.add(resourceRef);
                }
            }
            // 删除含有一个地址包含另外一个地址的
            Collections.sort(classesFolderResources);
            List<ResourceRef> toRemove = new LinkedList<ResourceRef>();
            for (int i = 0; i < classesFolderResources.size(); i++) {
                ResourceRef ref = classesFolderResources.get(i);
                String refURI = ref.getResource().getURI().toString();
                for (int j = i + 1; j < classesFolderResources.size(); j++) {
                    ResourceRef refj = classesFolderResources.get(j);
                    String refjURI = refj.getResource().getURI().toString();
                    if (refURI.startsWith(refjURI)) {
                        toRemove.add(refj);
                    } else if (refjURI.startsWith(refURI) && refURI.length() != refjURI.length()) {
                        toRemove.add(ref);
                    }
                }
            }
            classesFolderResources.removeAll(toRemove);
            //
            this.classesFolderResources = new ArrayList<ResourceRef>(classesFolderResources);
        }
        return Collections.unmodifiableList(classesFolderResources);
    }

    /**
     * 将要被扫描的jar资源
     * 
     * @param resourceLoader
     * @return
     * @throws IOException
     */
    public List<ResourceRef> getJarResources() throws IOException {
        if (jarResources == null) {
            List<ResourceRef> jarResources = new LinkedList<ResourceRef>();
            Resource[] metaInfResources = resourcePatternResolver.getResources("classpath*:/META-INF/");
            for (Resource metaInfResource : metaInfResources) {
                URL urlObject = metaInfResource.getURL();
                if (ResourceUtils.isJarURL(urlObject)) {
                    try {
                        String path = URLDecoder.decode(urlObject.getPath(), "UTF-8"); // fix 20%
                        if (path.startsWith("file:")) {
                            path = path.substring("file:".length(), path.lastIndexOf('!'));
                        } else {
                            path = path.substring(0, path.lastIndexOf('!'));
                        }
                        Resource resource = new FileSystemResource(path);
                        if (!jarResources.contains(resource)) {
                            ResourceRef ref = ResourceRef.toResourceRef(resource);
                            if (ref.getModifiers() != null) {
                                jarResources.add(ref);
                            }
                        }
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                }
            }
            this.jarResources = jarResources;
        }
        return Collections.unmodifiableList(jarResources);
    }
}
