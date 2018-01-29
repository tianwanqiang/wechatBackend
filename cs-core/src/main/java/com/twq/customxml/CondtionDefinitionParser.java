package com.twq.customxml;


import com.twq.util.CSLog;
import com.twq.util.PropertiesUtils;
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

public class CondtionDefinitionParser extends AbstractSingleBeanDefinitionParser {
    private final String DESC = "desc" ;
    private final String COND = "cond" ;
    private final String UNIT = "unit" ;
    private final String WORKLIST = "workList" ;
    private final String CONDEXPRESSION = "condExpression" ;


    private static Logger LOGGER = CSLog.get();
    /**
     * The bean that is created for this tag element
     * @param element The tag element
     * @return A FileFilterFactoryBean
     */
    @Override
    protected Class<?> getBeanClass(Element element) {
        return CondFactoryBean.class;
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
            ManagedList<Object> beanList = new ManagedList<Object>();
            BeanDefinition bean ;
            AbstractSingleBeanDefinitionParser parser ;
            ParserContext nestedCtx = new ParserContext(ctx.getReaderContext(), ctx.getDelegate(), builder.getBeanDefinition());
            NodeList nlist = element.getChildNodes();
            for (int i=0; i<nlist.getLength(); i++) {
                //DefinitionParserUtil.parseLimitedList(workList, nl.item(i), ctx, builder.getBeanDefinition(), element.getAttribute("scope"));
                Node node = nlist.item(i);
                if (node.getNodeType() == Element.ELEMENT_NODE){
                    String  childNodeName = node.getLocalName() ;
                    Element childElement  = (Element) node;
                    LOGGER.info("解析XML节点==>"+childNodeName);
                    if (COND.equals(childNodeName)) {
                        // Just make a new Parser for each one and let the parser do the work
                        parser = new CondtionDefinitionParser();
                        bean = parser.parse(childElement, nestedCtx);
                        LOGGER.info("      COND Bean==>"+bean);
                        beanList.add(bean);
                    }else if (UNIT.equals(childNodeName)) {
                        // Just make a new Parser for each one and let the parser do the work
                        parser = new UnitDefinitionParser();
                        bean = parser.parse(childElement, nestedCtx);
                        LOGGER.info("      UNIT Bean==>"+bean);
                        beanList.add(bean);
                    }

                }
            }
            builder.addPropertyValue(WORKLIST, beanList);
            builder.addPropertyValue(DESC, element.getAttribute(DESC));
            builder.addPropertyValue(CONDEXPRESSION, element.getAttribute(CONDEXPRESSION));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class CondFactoryBean implements FactoryBean<WorksUint> {
        private String desc = null;
        private String condExpression ;
        private List<WorksUint> workList;
        public WorksUint getObject() throws Exception {
            String condition_group_class = PropertiesUtils.getDynpropsMapperInstans().getProperty(Constants.condition_group_class);
            Class<?> wkGroup = Class.forName(condition_group_class);
            CondtionGroup condGroup = (CondtionGroup)wkGroup.newInstance();
            condGroup.setCond(condExpression);
            condGroup.setDesc(desc);
            condGroup.setWorkList(workList);
            return condGroup;
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
        public void setWorkList(List<WorksUint> workList) {
            this.workList = workList;
        }

        public void setCondExpression(String condExpression) {
            this.condExpression = condExpression;
        }
    }
}