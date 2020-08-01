// Date: 9/19/2013 11:00:58 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package com.mraof.minestuck.client.model;

import com.mraof.minestuck.entity.consort.IguanaEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class IguanaModel<T extends IguanaEntity> extends SegmentedModel<T>
{
	//fields
	ModelRenderer body;
	ModelRenderer rightLeg;
	ModelRenderer leftLeg;
	ModelRenderer head;
	ModelRenderer upperTail;
	ModelRenderer lowerTail;
	ModelRenderer upperJaw;
	ModelRenderer lowerJaw;
	ModelRenderer shape1;
	ModelRenderer shape2;

	public IguanaModel()
	{
		textureWidth = 64;
		textureHeight = 32;

		body = new ModelRenderer(this, 12, 18);
		body.addBox(-4F, 0F, -2F, 6, 8, 6);
		body.setRotationPoint(1F, 12F, -1F);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		rightLeg = new ModelRenderer(this, 0, 18);
		rightLeg.addBox(-2F, 0F, -2F, 2, 4, 3);
		rightLeg.setRotationPoint(-1F, 20F, 0F);
		rightLeg.mirror = true;
		setRotation(rightLeg, 0F, 0F, 0F);
		leftLeg = new ModelRenderer(this, 0, 25);
		leftLeg.addBox(-2F, 0F, -2F, 2, 4, 3);
		leftLeg.setRotationPoint(3F, 20F, 0F);
		leftLeg.mirror = true;
		setRotation(leftLeg, 0F, 0F, 0F);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-4F, -8F, -4F, 6, 4, 7);
		head.setRotationPoint(1F, 16F, 1F);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		upperTail = new ModelRenderer(this, 26, 0);
		upperTail.addBox(-2F, 0F, -2F, 2, 4, 2);
		upperTail.setRotationPoint(1F, 18F, 4F);
		upperTail.mirror = true;
		setRotation(upperTail, 0.2230717F, 0F, 0F);
		lowerTail = new ModelRenderer(this, 34, 0);
		lowerTail.addBox(-2F, 0F, -2F, 2, 2, 6);
		lowerTail.setRotationPoint(1F, 22F, 5F);
		lowerTail.mirror = true;
		setRotation(lowerTail, 0F, 0F, 0F);
		upperJaw = new ModelRenderer(this, 0, 11);
		upperJaw.addBox(0F, 0F, 0F, 4, 1, 3);
		upperJaw.setRotationPoint(-2F, 9F, -6F);
		upperJaw.mirror = true;
		setRotation(upperJaw, 0F, 0F, 0F);
		lowerJaw = new ModelRenderer(this, 14, 11);
		lowerJaw.addBox(0F, 0F, 0F, 4, 2, 3);
		lowerJaw.setRotationPoint(-2F, 10F, -6F);
		lowerJaw.mirror = true;
		setRotation(lowerJaw, 0F, 0F, 0F);
		shape1 = new ModelRenderer(this, 32, 16);
		shape1.addBox(0F, 0F, 0F, 1, 1, 1);
		shape1.setRotationPoint(-0.6F, 10.5F, 4F);
		shape1.mirror = true;
		setRotation(shape1, -0.9294653F, 0F, 0F);
		shape1.mirror = true;
		shape2 = new ModelRenderer(this, 36, 16);
		shape2.addBox(0F, 0F, 0F, 1, 2, 2);
		shape2.setRotationPoint(-0.6F, 8F, 4F);
		shape2.mirror = true;
		setRotation(shape1, -0.8179294F, 0F, 0F);
		shape2.mirror = false;
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		super.setRotationAngles(entity, f, f1, f2, f3, f4, f5);
	}

	@Override
	public void setRotationAngles(T entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.setRotationAngles(entity, f, f1, f2, f3, f4, f5);
	}

}
