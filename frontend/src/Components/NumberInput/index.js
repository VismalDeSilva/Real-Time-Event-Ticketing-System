import React, { useState } from 'react';
import styles from './style.module.scss';

const NumberInput = ({
	min = 1,
	max,
	value,
	onChange
}) => {
	const [inputValue, setInputValue] = useState(value || min);

	const handleDecrement = () => {
		const newValue = Math.max(min, inputValue - 1);
		setInputValue(newValue);
		onChange && onChange(newValue);
	};

	const handleIncrement = () => {
		const newValue = Math.min(max, inputValue + 1);
		setInputValue(newValue);
		onChange && onChange(newValue);
	};

	const handleInputChange = (e) => {
		const numValue = Number(e.target.value);
		if (!isNaN(numValue) && numValue >= min && numValue <= max) {
			setInputValue(numValue);
			onChange && onChange(numValue);
		}
	};

	return (
		<div className={styles.numberInputContainer}>
			<button
				className={`${styles.numberInputContainer__button} ${styles.numberInputContainer__buttonDecrement}`}
				onClick={handleDecrement}
			>
				-
			</button>
			<input
				type="number"
				className={styles.numberInputContainer__input}
				value={inputValue}
				onChange={handleInputChange}
				min={min}
				max={max}
			/>
			<button
				className={`${styles.numberInputContainer__button} ${styles.numberInputContainer__buttonIncrement}`}
				onClick={handleIncrement}
			>
				+
			</button>
		</div>
	);
};

export default NumberInput;