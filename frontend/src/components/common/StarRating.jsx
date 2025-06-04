import React from 'react';
import { Star } from 'lucide-react';

const StarRating = ({ rating, interactive = false, onRate = null }) => {
  return (
    <div className="flex gap-1">
      {[1, 2, 3, 4, 5].map(star => (
        <Star
          key={star}
          className={`w-4 h-4 ${
            star <= rating 
              ? 'fill-yellow-400 text-yellow-400' 
              : 'text-gray-300'
          } ${interactive ? 'cursor-pointer hover:text-yellow-400' : ''}`}
          onClick={interactive ? () => onRate(star) : undefined}
        />
      ))}
    </div>
  );
};

export default StarRating;