package com.twq.customxml;


import com.twq.util.CSLog;
import com.twq.util.PropertiesUtils;
import com.twq.worksflow.WorksGroup;
import com.twq.worksflow.WorksUint;
import org.slf4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public class ApiDefinitionParser  extends AbstractSingleBeanDefinitionParser {
    private final String desc = "desc" ;
    private final String cond = "cond" ;
    private final String unit = "unit" ;
    private final String workList = "workList" ;
    private static Logger LOGGER = CSLog.get();

    /**
     * The bean that is created for this tag element
     * @param element The tag element
     * @return A FileListFactoryBean
     */
    @Override
    protected Class<?> getBeanClass(Element element) {
        return WorksGroupFactoryBean.class;
    }
    /**
     * Called when the fileList tag is to be parsed
     * @param element The tag element
     * @param ctx The context in which the parsing is occuring
     * @param builder The bean definitions build to use
     */
    @Override
    protected void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder builder) {
        ParserContext nestedCtx = new ParserContext(ctx.getReaderContext(), ctx.getDelegate(), builder.getBeanDefinition());
        ManagedList<Object> beanList = new ManagedList<Object>();
        NodeList nlist = element.getChildNodes();
        AbstractSingleBeanDefinitionParser parser ;
        BeanDefinition bean ;
        for (int i=0; i<nlist.getLength(); i++) {
            Node node = nlist.item(i);
            if (node.getNodeType() == Element.ELEMENT_NODE){
                String  childNodeName = node.getLocalName() ;
                Element childElement  = (Element) node;
                LOGGER.info("____解析自定义xml-->"+childNodeName);
                if (cond.equals(childNodeName)) {
                    // Just make a new Parser for each one and let the parser do the work
                    parser = new CondtionDefinitionParser();
                    bean = parser.parse(childElement, nestedCtx);
                    LOGGER.info("                    condtion:"+bean);
                    beanList.add(bean);
                }else if (unit.equals(childNodeName)) {
                    // Just make a new Parser for each one and let the parser do the work
                    parser = new UnitDefinitionParser();
                    bean = parser.parse(childElement, nestedCtx);
                    LOGGER.info("                    serviceUnit:"+bean);
                    beanList.add(bean);
                }
            }else {
            }
        }
        builder.addPropertyValue(workList, beanList);
        builder.addPropertyValue(desc, element.getAttribute(desc));
    }

    public static class WorksGroupFactoryBean implements FactoryBean<WorksGroup> {
        private String desc ;
        private List<WorksUint> workList ;
        public WorksGroup getObject() throws Exception {
             String works_group_class = PropertiesUtils.getDynpropsMapperInstans().getProperty(Constants.works_group_class);
             Class<?> wkGroup = Class.forName(works_group_class);
             WorksGroup worksGroup = (WorksGroup)wkGroup.newInstance();
             worksGroup.setWorkList(workList);
             worksGroup.setDesc(desc);
             return worksGroup;
        }
        public Class<WorksGroup> getObjectType() {
            return WorksGroup.class;
        }
        public boolean isSingleton() {
            return true;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public void setWorkList(List<WorksUint> workList) {
            this.workList = workList ;
        }
    }
}