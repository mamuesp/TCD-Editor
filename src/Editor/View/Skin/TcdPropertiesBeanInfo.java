package Editor.View.Skin;

import java.awt.*;
import java.beans.*;

/**
 * Part of project: TCD-Editor
 * <p>
 * Created by M. Mueller-Spaeth on 02.04.16.
 *
 * @version ${Version}
 */
public class TcdPropertiesBeanInfo implements BeanInfo {
    /**
     * Returns the bean descriptor
     * that provides overall information about the bean,
     * such as its display name or its customizer.
     *
     * @return a {@link BeanDescriptor} object,
     * or {@code null} if the information is to
     * be obtained through the automatic analysis
     */
    @Override
    public BeanDescriptor getBeanDescriptor() {
        return null;
    }

    /**
     * Returns the event descriptors of the bean
     * that define the types of events fired by this bean.
     *
     * @return an array of {@link EventSetDescriptor} objects,
     * or {@code null} if the information is to
     * be obtained through the automatic analysis
     */
    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return new EventSetDescriptor[0];
    }

    /**
     * A bean may have a default event typically applied when this bean is used.
     *
     * @return index of the default event in the {@code EventSetDescriptor} array
     * returned by the {@code getEventSetDescriptors} method,
     * or -1 if there is no default event
     */
    @Override
    public int getDefaultEventIndex() {
        return 0;
    }

    /**
     * Returns descriptors for all properties of the bean.
     * <p>
     * If a property is indexed, then its entry in the result array
     * belongs to the {@link IndexedPropertyDescriptor} subclass
     * of the {@link PropertyDescriptor} class.
     * A client of the {@code getPropertyDescriptors} method
     * can use the {@code instanceof} operator to check
     * whether a given {@code PropertyDescriptor}
     * is an {@code IndexedPropertyDescriptor}.
     *
     * @return an array of {@code PropertyDescriptor} objects,
     * or {@code null} if the information is to
     * be obtained through the automatic analysis
     */
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return new PropertyDescriptor[0];
    }

    /**
     * A bean may have a default property commonly updated when this bean is customized.
     *
     * @return index of the default property in the {@code PropertyDescriptor} array
     * returned by the {@code getPropertyDescriptors} method,
     * or -1 if there is no default property
     */
    @Override
    public int getDefaultPropertyIndex() {
        return 0;
    }

    /**
     * Returns the method descriptors of the bean
     * that define the externally visible methods supported by this bean.
     *
     * @return an array of {@link MethodDescriptor} objects,
     * or {@code null} if the information is to
     * be obtained through the automatic analysis
     */
    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return new MethodDescriptor[0];
    }

    /**
     * This method enables the current {@code BeanInfo} object
     * to return an arbitrary collection of other {@code BeanInfo} objects
     * that provide additional information about the current bean.
     * <p>
     * If there are conflicts or overlaps between the information
     * provided by different {@code BeanInfo} objects,
     * the current {@code BeanInfo} object takes priority
     * over the additional {@code BeanInfo} objects.
     * Array elements with higher indices take priority
     * over the elements with lower indices.
     *
     * @return an array of {@code BeanInfo} objects,
     * or {@code null} if there are no additional {@code BeanInfo} objects
     */
    @Override
    public BeanInfo[] getAdditionalBeanInfo() {
        return new BeanInfo[0];
    }

    /**
     * Returns an image that can be used to represent the bean in toolboxes or toolbars.
     * <p>
     * There are four possible types of icons:
     * 16 x 16 color, 32 x 32 color, 16 x 16 mono, and 32 x 32 mono.
     * If you implement a bean so that it supports a single icon,
     * it is recommended to use 16 x 16 color.
     * Another recommendation is to set a transparent background for the icons.
     *
     * @param iconKind the kind of icon requested
     * @return an image object representing the requested icon,
     * or {@code null} if no suitable icon is available
     * @see #ICON_COLOR_16x16
     * @see #ICON_COLOR_32x32
     * @see #ICON_MONO_16x16
     * @see #ICON_MONO_32x32
     */
    @Override
    public Image getIcon(int iconKind) {
        return null;
    }
}
