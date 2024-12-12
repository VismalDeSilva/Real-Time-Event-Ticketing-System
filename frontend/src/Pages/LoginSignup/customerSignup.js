import React from 'react'
import styles from './style.module.scss'
import { Helmet } from 'react-helmet'
import Navbar from '../../Components/Navbar'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'

const CustomerSignup = () => {

	const navigate = useNavigate();

	const handleCustomerCreateAccount = async (e) => {
		e.preventDefault();
		try {
			const response = await axios.post('http://localhost:8080/api/customers', {
				name: e.target.name.value,
				email: e.target.email.value,
				phoneNumber: e.target.phoneNumber.value,
				password: e.target.password.value
			});
			if (response.status === 200) {
				alert('Customer account created successfully');
				navigate('/customer/login');
			} else {
				alert('Error creating customer account');
			}

		} catch (error) {
			console.error('Error creating customer account:', error);
		}
	}
	return (
		<div className={styles.container} >
			<Helmet><title>Customer Signup</title></Helmet>
			<Navbar />
			<div className={styles.signupCont}>
				<h2>Customer Signup</h2>
				<form onSubmit={handleCustomerCreateAccount}>
					<input type="text" name="name" placeholder="Name" />
					<input type="email" name="email" placeholder="Email" />
					<input type="text" name="phoneNumber" placeholder="Phone Number" />
					<input type="password" name="password" placeholder="Password" />
					<button type="submit">Signup</button>
				</form>
			</div>
		</div>
	)
}

export default CustomerSignup