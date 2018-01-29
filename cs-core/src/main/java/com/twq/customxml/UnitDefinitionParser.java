package com.twq.customxml;


import com.twq.util.StringUtil;
import com.twq.worksflow.WorksUint;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class UnitDefinitionParser extends AbstractSingleBeanDefinitionParser {
    /**
     * The bean that is created for this tag element
     * @param element The tag element
     * @return A FileFilterFactoryBean
     */
    @Override
    protected Class<?> getBeanClass(Element element) {
        return UnitFactoryBean.class;
    }
    /**
     * Called when the fileFilter tag is to be parsed
     * @param element The tag element
     * @param ctx The context in which the parsing is occuring
     * @param builder The bean definitions build to use
     * */
    @Override
    protected void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder builder) {
        try {
            String ref       = element.getAttribute("ref");
            String className = element.getAttribute("class");
            if(StringUtil.isNotEmpty(ref)){
                builder.addPropertyValue("worksUint", DefinitionParserUtil.getObjectByRef(element, ctx));
                builder.addPropertyValue("desc", element.getAttribute("desc"));
            }else if(StringUtil.isNotEmpty(className)){
                builder.addPropertyValue("worksUint", DefinitionParserUtil.getObjectByClass(element, ctx,builder.getBeanDefinition()));
                builder.addPropertyValue("desc", element.getAttribute("desc"));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class UnitFactoryBean implements FactoryBean<WorksUint> {
        private String desc = null;
        private WorksUint worksUint = null;

        public WorksUint getObject() throws Exception {
            worksUint.setDesc(desc);
            return worksUint;
        }
        public Class<?> getObjectType() {
            return WorksUint.class;
        }
        public boolean isSingleton() {
            return true;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public void setWorksUint(WorksUint worksUint) {
            this.worksUint = worksUint;
        }
    }
}