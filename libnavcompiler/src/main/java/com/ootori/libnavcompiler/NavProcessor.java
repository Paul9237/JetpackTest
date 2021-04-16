package com.ootori.libnavcompiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.auto.service.AutoService;
import com.ootori.libnavannotation.ActivityDestination;
import com.ootori.libnavannotation.FragmentDestination;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.ootori.libnavannotation.ActivityDestination", "com.ootori.libnavannotation.FragmentDestination"})
public class NavProcessor extends AbstractProcessor {

    private static final String OUTPUT_NAME = "destination.json";
    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> activityElements = roundEnvironment.getElementsAnnotatedWith(ActivityDestination.class);
        Set<? extends Element> fragmentElements = roundEnvironment.getElementsAnnotatedWith(FragmentDestination.class);
        if (!activityElements.isEmpty() || !fragmentElements.isEmpty()) {
            HashMap<String, JSONObject> map = new HashMap<>();
            processElements(activityElements, ActivityDestination.class, map);
            processElements(fragmentElements, FragmentDestination.class, map);

            FileWriter writer = null;
            try {
                FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_NAME);
                String resourcePath = resource.toUri().getPath();
                String appPath = resourcePath.substring(0, resourcePath.indexOf("app") + 3);
                String assetsPath = String.format("%s/src/main/assets/", appPath);

                File file = new File(assetsPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File outputFile = new File(file, OUTPUT_NAME);
                if (file.exists()) {
                    outputFile.delete();
                }
                outputFile.createNewFile();
                String json = JSON.toJSONString(map);
                writer = new FileWriter(outputFile);
                writer.append(json);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return true;
    }

    private void processElements(Set<? extends Element> elements,
                                 Class<? extends Annotation> destinationClass,
                                 HashMap<String, JSONObject> outputMap) {
        for (Element element : elements) {
            TypeElement e = (TypeElement) element;
            String pageUrl = null;
            String className = e.getQualifiedName().toString();
            int id = Math.abs(className.hashCode());
            boolean needLogin = false;
            boolean asStarter = false;
            boolean isFragment = false;

            Annotation annotation = e.getAnnotation(destinationClass);
            if (annotation instanceof ActivityDestination) {
                ActivityDestination destination = (ActivityDestination) annotation;
                pageUrl = destination.pageUrl();
                needLogin = destination.needLogin();
                asStarter = destination.asStarter();
                isFragment = false;
            } else if (annotation instanceof FragmentDestination) {
                FragmentDestination destination = (FragmentDestination) annotation;
                pageUrl = destination.pageUrl();
                needLogin = destination.needLogin();
                asStarter = destination.asStarter();
                isFragment = true;
            } else {
                continue;
            }

            if (outputMap.containsKey(pageUrl)) {
                messager.printMessage(Diagnostic.Kind.ERROR, String.format("已经有存在的页面[%s]了", className));
                continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("pageUrl", pageUrl);
            jsonObject.put("needLogin", needLogin);
            jsonObject.put("asStarter", asStarter);
            jsonObject.put("isFragment", isFragment);
            jsonObject.put("className", className);
            outputMap.put(pageUrl, jsonObject);
        }
    }
}
